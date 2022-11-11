package com.example.transactionpaypal;

import com.example.paypal_model.Transaction;
import com.example.paypal_model.TransactionPojo;

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
  private transactionJdbcRepository jdbcRepository;

  private final RestTemplate restTemplate = new RestTemplate();

  /**
   * Tramite restTemplate recupera gli id dei due utenti
   * per poi eseguire la transazione. Infine richiama un altro
   * metodo per completare in maniera asincrona la transazione.
   */
  public void returnTransaction(String user1, String user2, Float saldo) {

    final ResponseEntity<Integer> id1Entity = this.restTemplate.getForEntity(gestioneBalanceUrl
        + "findID/" + user1, Integer.class);
    final ResponseEntity<Integer> id2Entity = this.restTemplate.getForEntity(gestioneBalanceUrl
        + "findID/" + user2, Integer.class);

    final Integer id1 = id1Entity.getBody();
    final Integer id2 = id2Entity.getBody();

    final TransactionMoney dto = new TransactionMoney();
    dto.setUser1(user1);
    dto.setUser2(user2);
    dto.setSaldo(saldo);

    completeTransaction(id1, id2, dto);

        /*
        return "COMPLETE";
         */
  }

  /**
   * Setta i valori della transazione nel dto e setta anche lo stato
   * della transazione a CREATED e salva la transazione sul database.
   * Utilizza la classe comune ai due moduli, ovvero Transaction, per settare
   * i campi e l'id della transazione.
   * Ricerca la transazione nel db, aggiorna lo stato a pending e procede a
   * chiamare in rest template la post del metodo dell'altra liberia che completa la transazione
   * e aggiorna i valori degli utenti nel db.
   */
  @Async
  public void completeTransaction(Integer id1, Integer id2, TransactionMoney dto) {

    dto.setStatoTransazione(TransactionMoney.Stato.CREATED);

        /*
        this.transazioneRepository.save(dto);
         */
    final Integer id = this.jdbcRepository.save(dto);

    final Transaction t = new Transaction();
    t.setIdTra(id);
    t.setIdStart(id1);
    t.setIdEnd(id2);
    t.setMoney(dto.getSaldo());

    final TransactionMoney transazionePerModificaStato = this.jdbcRepository.findById(id);
    transazionePerModificaStato.setStatoTransazione(TransactionMoney.Stato.PENDING);
    this.jdbcRepository.updateStatus(id, TransactionMoney.Stato.PENDING.name());

    this.restTemplate.postForEntity(gestioneBalanceUrl
        + "transaction", t, String.class);

        /*
        return transazione;
         */
  }

  /**
   * Preleva l'id e l stuatus dai campi del TransactionPojo
   * e richiama il metodo che ne fa il lavoro.
   */
  public void updateStatus(TransactionPojo transactionPojo) {

    final Integer id = Integer.parseInt(transactionPojo.getTransactionId());

    this.jdbcRepository.updateStatus(id, transactionPojo.getStatus());
  }
}
