package com.amaris.it.paypal.transaction.consumer;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaOrderListener {

  @Autowired
  TransactionRepository transactionRepository;

  @KafkaListener(topics = "Transaction", groupId = "group_id")
  public void listen(String transactionResult) throws IOException {
    System.out.println("Received order : " + transactionResult);
    /*Gson g = new Gson();
    TransactionResult p = g.fromJson(transactionResult, TransactionResult.class);*/
    ObjectMapper mapper = new ObjectMapper();
    JsonNode actualObj = mapper.readTree(transactionResult);
    transactionRepository.updateStatus(actualObj.get("transactionId").asLong(), TransactionResult.TransactionStatus.valueOf(actualObj.get("status").asText()));
  }
}
