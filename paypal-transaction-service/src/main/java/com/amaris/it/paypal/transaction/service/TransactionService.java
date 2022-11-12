package com.amaris.it.paypal.transaction.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.model.Transaction;
import com.amaris.it.paypal.transaction.repository.TransactionJdbcRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {

  @Value("${user_service.url}")
  private String userServiceUrl;

  @Autowired
  private TransactionJdbcRepository jdbcRepository;
  @Autowired
  private RestTemplate restTemplate;

  /**
   * Tramite restTemplate recupera gli id dei due utenti
   * per poi eseguire la transazione. Infine richiama un altro
   * metodo per completare in maniera asincrona la transazione.
   */
  public void returnTransaction(String user1, String user2, Double money) {

    final ResponseEntity<Long> id1Entity = this.restTemplate.getForEntity(userServiceUrl
        + "findID/" + user1, Long.class);
    final ResponseEntity<Long> id2Entity = this.restTemplate.getForEntity(userServiceUrl
        + "findID/" + user2, Long.class);

    final Long id1 = id1Entity.getBody();
    final Long id2 = id2Entity.getBody();

    final Transaction dto = new Transaction();
    dto.setSenderUsername(user1);
    dto.setReceiverUsername(user2);
    dto.setAmount(money);

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
  public void completeTransaction(Long id1, Long id2, Transaction dto) {

    dto.setTransactionStatus(TransactionResult.TransactionStatus.CREATED);

    final Long id = this.jdbcRepository.save(dto);

    final TransactionRequest t = new TransactionRequest();
    t.setTransactionId(id);
    t.setSenderUserId(id1);
    t.setReceiverUserId(id2);
    t.setAmount(dto.getAmount());

    final Transaction transazionePerModificaStato = this.jdbcRepository.findById(id);
    transazionePerModificaStato.setTransactionStatus(TransactionResult.TransactionStatus.PENDING);
    this.jdbcRepository.updateStatus(id, TransactionResult.TransactionStatus.PENDING);

    this.restTemplate.postForEntity(userServiceUrl
        + "transaction", t, String.class);
  }

  /**
   * Preleva l'id e l stuatus dai campi del TransactionResult
   * e richiama il metodo che ne fa il lavoro.
   */
  public void updateStatus(TransactionResult transactionPojo) {

    final Long id = transactionPojo.getTransactionId();

    this.jdbcRepository.updateStatus(id, transactionPojo.getStatus());
  }
}
