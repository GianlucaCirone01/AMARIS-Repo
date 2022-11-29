package com.amaris.it.paypal.transaction.consumer;

import com.google.gson.Gson;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "deploy.notifier", havingValue = "kafka")
public class KafkaOrderListener {

  @Autowired
  TransactionRepository transactionRepository;

  @KafkaListener(topics = "Transaction", groupId = "group_id")
  public void listen(String transactionResult) {
    System.out.println("Received order : " + transactionResult);
    Gson g = new Gson();
    TransactionResult p = g.fromJson(transactionResult, TransactionResult.class);
    transactionRepository.updateStatus(p.getTransactionId(), p.getStatus());
  }
}
