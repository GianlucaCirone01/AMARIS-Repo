package com.amaris.it.paypal.transaction.consumer;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
  public void listen(String transactionResult) throws JsonProcessingException {
    System.out.println("Received order : " + transactionResult);
    
    ObjectMapper mapper = new ObjectMapper();
    TransactionResult actualObj = mapper.readValue(transactionResult, TransactionResult.class);
    transactionRepository.updateStatus(actualObj.getTransactionId(), actualObj.getStatus());
  }
}
