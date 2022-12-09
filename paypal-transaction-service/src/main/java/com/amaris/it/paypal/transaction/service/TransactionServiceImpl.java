package com.amaris.it.paypal.transaction.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.connector.UserServiceConnector;
import com.amaris.it.paypal.transaction.model.Transaction;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private UserServiceConnector userServiceConnector;
  @Autowired
  private RestTemplate restTemplate;

  @Value("${transaction.pending.time}")
  private Long time;

  @Value("${transaction.created.time}")
  private Long time2;

  @Override
  public void createTransaction(String senderUsername, String receiverUsername, Double amount) {

    final Long senderUserId = userServiceConnector.getIdByUsername(senderUsername);
    final Long receiverUserId = userServiceConnector.getIdByUsername(receiverUsername);

    final Transaction dto = new Transaction();
    Timestamp tm = new Timestamp(System.currentTimeMillis());
    dto.setSender(senderUsername);
    dto.setReceiver(receiverUsername);
    dto.setAmount(amount);
    dto.setTransactionStatus(TransactionResult.TransactionStatus.CREATED);
    dto.setCreationDate(tm);

    final Long id = this.transactionRepository.save(dto);

    retryTransaction(senderUserId, receiverUserId, dto, id);
  }

  @Override
  public void updateStatus(TransactionResult transactionPojo) {

    final Long id = transactionPojo.getTransactionId();

    this.transactionRepository.updateStatus(id, transactionPojo.getStatus());
  }

  /*
   *cancellare tutti i println
   */
  /*
  @Override
  @Scheduled(fixedDelay = 3000)
  public void checkStatusTransactionP() {
    List<Transaction> transactionList = new ArrayList<>();
    transactionList = transactionRepository.getTransactionStatus("PENDING");
    System.out.println("LISTA TRANSAZIONI PENDING " + transactionList);

    Date now = new Date();
    System.out.println("NOW : " + now);

    for (Transaction list : transactionList) {
      long diff = now.getTime() - list.getCreationDate().getTime();
      System.out.println("DIFF: " + diff);
      if (diff > time) {
        transactionRepository.updateStatus(list.getId(), TransactionResult.TransactionStatus.ABORTED);
      }
    }
  }
   */

  public void retryTransaction(Long senderUserId, Long receiverUserId, Transaction dto, Long id) {
    final TransactionRequest transactionRequest = new TransactionRequest();
    transactionRequest.setTransactionId(id);
    transactionRequest.setSenderUserId(senderUserId);
    transactionRequest.setReceiverUserId(receiverUserId);
    transactionRequest.setAmount(dto.getAmount());
    transactionRequest.setAmount(dto.getAmount());

    userServiceConnector.requestTransaction(transactionRequest);

    this.transactionRepository.updateStatus(id, TransactionResult.TransactionStatus.PENDING);
  }

  @Override
  @Scheduled(fixedDelay = 3000)
  public void checkStatusTransactionC() {
    List<Transaction> transactionList = new ArrayList<>();
    transactionList = transactionRepository.getTransactionStatus("CREATED");
    System.out.println("LISTA TRANSAZIONI CREATED " + transactionList);

    Date now = new Date();
    System.out.println("NOW : " + now);

    for (Transaction t : transactionList) {
      long diff = now.getTime() - t.getCreationDate().getTime();
      System.out.println(diff);
      if (diff > time2) {
        final Long senderUserId = userServiceConnector.getIdByUsername(t.getSender());
        final Long receiverUserId = userServiceConnector.getIdByUsername(t.getReceiver());

        transactionRepository.updateStatus(t.getId(), TransactionResult.TransactionStatus.PENDING);
        retryTransaction(senderUserId, receiverUserId, t, t.getId());
      }
    }
  }
}
