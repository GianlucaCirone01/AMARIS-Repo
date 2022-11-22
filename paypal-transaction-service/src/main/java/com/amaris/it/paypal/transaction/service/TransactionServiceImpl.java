package com.amaris.it.paypal.transaction.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.connector.UserServiceConnector;
import com.amaris.it.paypal.transaction.model.Transaction;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private UserServiceConnector userServiceConnector;

  public TransactionServiceImpl(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

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
  public void updateStatus(TransactionResult transactionPojo) {

    final Long id = transactionPojo.getTransactionId();

    this.transactionRepository.updateStatus(id, transactionPojo.getStatus());
  }
}
