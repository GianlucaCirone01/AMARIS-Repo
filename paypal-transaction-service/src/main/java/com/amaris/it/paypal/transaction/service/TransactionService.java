package com.amaris.it.paypal.transaction.service;

import com.amaris.it.paypal.messages.model.TransactionPojo;
import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.transaction.entity.TransactionMoney;
import com.amaris.it.paypal.transaction.repository.TransactionJdbcRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {

  @Value("${gestione_balance.url}")
  private String gestioneBalanceUrl;

  @Autowired
  private TransactionJdbcRepository jdbcRepository;

  private final RestTemplate restTemplate = new RestTemplate();

  /**
   * Tramite restTemplate recupera gli id dei due utenti
   * per poi eseguire la transazione. Infine richiama un altro
   * metodo per completare in maniera asincrona la transazione.
   */
  public void returnTransaction(String user1, String user2, Double money) {

    final ResponseEntity<Long> id1Entity = this.restTemplate.getForEntity(gestioneBalanceUrl
        + "findID/" + user1, Long.class);
    final ResponseEntity<Long> id2Entity = this.restTemplate.getForEntity(gestioneBalanceUrl
        + "findID/" + user2, Long.class);

    final Long id1 = id1Entity.getBody();
    final Long id2 = id2Entity.getBody();

    final TransactionMoney dto = new TransactionMoney();
    dto.setUser1(user1);
    dto.setUser2(user2);
    dto.setMoney(money);

    completeTransaction(id1, id2, dto);
  }

  /**
   * Setta i valori della transazione nel dto e setta anche lo stato
   * della transazione a CREATED e salva la transazione sul database.
   * Utilizza la classe comune ai due moduli, ovvero TransactionRequest, per settare
   * i campi e l'id della transazione.
   * Ricerca la transazione nel db, aggiorna lo stato a pending e procede a
   * chiamare in rest template la post del metodo dell'altra liberia che completa la transazione
   * e aggiorna i valori degli utenti nel db.
   */
  @Async
  public void completeTransaction(Long id1, Long id2, TransactionMoney dto) {

    dto.setStatusTransaction(TransactionMoney.Status.CREATED);

    final Long id = this.jdbcRepository.save(dto);

    final TransactionRequest t = new TransactionRequest();
    t.setTransactionId(id);
    t.setSenderUserId(id1);
    t.setReceiverUserId(id2);
    t.setAmount(dto.getMoney());

    final TransactionMoney transazionePerModificaStato = this.jdbcRepository.findById(id);
    transazionePerModificaStato.setStatusTransaction(TransactionMoney.Status.PENDING);
    this.jdbcRepository.updateStatus(id, TransactionMoney.Status.PENDING.name());

    this.restTemplate.postForEntity(gestioneBalanceUrl
        + "transaction", t, String.class);
  }

  /**
   * Preleva l'id e l stuatus dai campi del TransactionPojo
   * e richiama il metodo che ne fa il lavoro.
   */
  public void updateStatus(TransactionPojo transactionPojo) {

    final Long id = transactionPojo.getTransactionId();

    this.jdbcRepository.updateStatus(id, transactionPojo.getStatus());
  }
}
