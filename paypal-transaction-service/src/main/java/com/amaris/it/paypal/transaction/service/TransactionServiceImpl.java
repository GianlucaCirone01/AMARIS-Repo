package com.amaris.it.paypal.transaction.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.connector.UserServiceConnector;
import com.amaris.it.paypal.transaction.model.Transaction;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
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

    final Transaction dto = new Transaction();
    dto.setSenderUsername(senderUsername);
    dto.setReceiverUsername(receiverUsername);
    dto.setAmount(amount);
    dto.setTransactionStatus(TransactionResult.TransactionStatus.CREATED);

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
      Date executionDate) {

    final Transaction dto = new Transaction();
    dto.setSenderUsername(senderUsername);
    dto.setReceiverUsername(receiverUsername);
    dto.setAmount(amount);
    dto.setTransactionStatus(TransactionResult.TransactionStatus.CREATED);
    dto.setExecutionDate(executionDate);

    final Long id = this.transactionRepository.save(dto);
  }

  @Override
  @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
  public void executionTransaction() {

    LOGGER.log(Level.INFO, "I am ready to execute Transactions");

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formatDateTime = now.format(formatter);

    List<Transaction> transactionList = this.transactionRepository.selectForADate(Date.valueOf(formatDateTime)
        , TransactionResult.TransactionStatus.CREATED);

    transactionList.forEach(transaction -> {

      final Long senderUserId = userServiceConnector.getIdByUsername(transaction.getSenderUsername());
      final Long receiverUserId = userServiceConnector.getIdByUsername(transaction.getReceiverUsername());

      final TransactionRequest transactionRequest = new TransactionRequest(
          transaction.getTransactionId()
          , senderUserId
          , receiverUserId
          , transaction.getAmount()
          , transaction.getExecutionDate()
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
}
