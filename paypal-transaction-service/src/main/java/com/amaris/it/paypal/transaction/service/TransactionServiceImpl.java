package com.amaris.it.paypal.transaction.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.connector.UserServiceConnector;
import com.amaris.it.paypal.transaction.model.Transaction;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private UserServiceConnector userServiceConnector;

  private static final Logger LOGGER = Logger.getLogger(TransactionServiceImpl.class.getName());

  @Override
  public void createTransaction(String senderUsername, String receiverUsername, Double amount) {

    final Long senderUserId = userServiceConnector.getIdByUsername(senderUsername);
    final Long receiverUserId = userServiceConnector.getIdByUsername(receiverUsername);

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formatDateTime = now.format(formatter);

    final Transaction dto = new Transaction();
    dto.setSenderUsername(senderUsername);
    dto.setReceiverUsername(receiverUsername);
    dto.setAmount(amount);
    dto.setTransactionStatus(TransactionResult.TransactionStatus.CREATED);
    dto.setCreationDate(Timestamp.valueOf(formatDateTime));

    final Long id = this.transactionRepository.save(dto);

    final TransactionRequest transactionRequest = new TransactionRequest();
    transactionRequest.setTransactionId(id);
    transactionRequest.setSenderUserId(senderUserId);
    transactionRequest.setReceiverUserId(receiverUserId);
    transactionRequest.setAmount(dto.getAmount());

    userServiceConnector.requestTransaction(transactionRequest);

    this.transactionRepository.updateStatus(id, TransactionResult.TransactionStatus.PENDING);
  }

  @Override
  public void createTransactionForADate(String senderUsername, String receiverUsername,
      Double amount,
      Timestamp executionDate) {

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formatDateTime = now.format(formatter);

    final Transaction dto = new Transaction();
    dto.setSenderUsername(senderUsername);
    dto.setReceiverUsername(receiverUsername);
    dto.setAmount(amount);
    dto.setTransactionStatus(TransactionResult.TransactionStatus.CREATED);
    dto.setExecutionDate(executionDate);
    dto.setCreationDate(Timestamp.valueOf(formatDateTime));

    final Long id = this.transactionRepository.save(dto);
  }

  @Override
  @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
  public void executionTransaction() {

    LOGGER.log(Level.INFO, "I am ready to EXECUTE Transactions");

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formatDateTime = now.format(formatter);

    List<Transaction> transactionList = this.transactionRepository.selectForADate(Timestamp.valueOf(formatDateTime)
        , TransactionResult.TransactionStatus.CREATED);

    transactionList.forEach(transaction -> {

      final Long senderUserId = userServiceConnector.getIdByUsername(transaction.getSenderUsername());
      final Long receiverUserId = userServiceConnector.getIdByUsername(transaction.getReceiverUsername());

      final TransactionRequest transactionRequest = new TransactionRequest(
          transaction.getTransactionId()
          , senderUserId
          , receiverUserId
          , transaction.getAmount()
      );
      userServiceConnector.requestTransaction(transactionRequest);
      this.transactionRepository.updateStatus(transaction.getTransactionId()
          , TransactionResult.TransactionStatus.PENDING);

    });
  }

  @Override
  public void updateStatus(TransactionResult transactionPojo) {

    final Long id = transactionPojo.getTransactionId();

    this.transactionRepository.updateStatus(id, transactionPojo.getStatus());
  }

  // TODO se created senza data riprovare la transazione


  @Override
  @Scheduled(fixedDelayString = "${fixedDelay2.in.milliseconds}")
  public void retryTransaction() {
    LOGGER.log(Level.INFO, "I am ready to RETRY Transaction");

    List<Transaction> transactionList = this.transactionRepository.selectByStatus(
        TransactionResult.TransactionStatus.CREATED);

    transactionList.forEach(transaction -> {
      final Long senderUserId = userServiceConnector.getIdByUsername(transaction.getSenderUsername());
      final Long receiverUserId = userServiceConnector.getIdByUsername(transaction.getReceiverUsername());

      final TransactionRequest transactionRequest = new TransactionRequest(
          transaction.getTransactionId()
          , senderUserId
          , receiverUserId
          , transaction.getAmount()
      );
      userServiceConnector.requestTransaction(transactionRequest);
      this.transactionRepository.updateStatus(transaction.getTransactionId()
          , TransactionResult.TransactionStatus.PENDING);

    });
  }
  // TODO se pending dopo un tot di tempo dalla creazione mettere ad Aborted

}
