package com.amaris.it.paypal.transaction.consumer;

import com.amaris.it.paypal.messages.model.TransactionResult;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderListener {

  @KafkaListener(topics = "Transaction", groupId = "group_id")
  public void listen(TransactionResult transactionResult) {

    System.out.println("Received order : " + transactionResult);
  }
}
