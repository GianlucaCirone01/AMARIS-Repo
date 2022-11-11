package com.amaris.transactionpaypal.service;


import com.amaris.paypalmodel.model.TransactionPojo;
import com.amaris.paypalmodel.model.TransferBalance;
import com.amaris.transactionpaypal.entity.Transaction;
import com.amaris.transactionpaypal.model.TransferMoney;
import com.amaris.transactionpaypal.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@EnableAsync
@Service
public class TransactionService {
  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private RestTemplate restTemplate;

  @PersistenceContext
  private EntityManager entityManager;

  @Value("${getIdByUsername.url}")
  private String getIdByUsernameUrl;
  @Value("${transferMoney.url}")
  private String transferMoneyUrl;

  public Transaction findOne(int id) {
    return transactionRepository.findById(id).orElse(null);
  }

  public String findStatusById(int id) {
    return entityManager.createQuery("select statoTransizione from Transaction t where t.id = :id").toString();
  }

  public Transaction saveTransaction(Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  public List<Transaction> getAllTransaction() {
    return transactionRepository.findAll();
  }


  public Optional<Transaction> getById(int id) {
    return transactionRepository.findById(id);
  }

  /*metodo che recupera l'id tramite username*/
  public int getIdByUsername(String username) {
    String url = getIdByUsernameUrl + username;
    return restTemplate.getForObject(url, int.class);
  }

  /*metodo che comunica tramite restemplate, e che esegue il treaferimento soldi*/
  @Async
  public void transferMoneyCallRestTemplate(TransferMoney transferBalance) {

    int id = getIdByUsername(transferBalance.getUsernameMittente());
    int id2 = getIdByUsername(transferBalance.getUsernameDestinatario());

    Transaction transaction = new Transaction(transferBalance.getUsernameMittente(),
        transferBalance.getUsernameDestinatario(), transferBalance.getBalance(),
        Transaction.StatoTransizione.created);
    saveTransaction(transaction);
    TransferBalance transferBalance1 = new TransferBalance(id, id2, transferBalance.getBalance(), transaction.getId());
    restTemplate.postForEntity(transferMoneyUrl, transferBalance1, Void.class).getBody();
  }

  /*metodo che aggiorna lo status della transazione*/
  public void updateStatus(TransactionPojo transaction) {
    if (transaction.getStatoTransazione().equals("created")) {
      Transaction tr = findOne(transaction.getIdTransaction());
      tr.setStatoTransizione(Transaction.StatoTransizione.completed);
      Transaction attachedUser = transactionRepository.save(tr);
    } else {
      Transaction tr = findOne(transaction.getIdTransaction());
      tr.setStatoTransizione(Transaction.StatoTransizione.error);
      Transaction attachedUser = transactionRepository.save(tr);
    }
  }
}


