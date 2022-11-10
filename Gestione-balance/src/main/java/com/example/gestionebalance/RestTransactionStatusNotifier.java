package com.example.gestionebalance;

import com.example.paypal_model.TransactionPojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTransactionStatusNotifier implements TransactionStatusNotifier {

  @Value("${transaction_paypal.url}")
  private String transactionPaypalUrl;
  private RestTemplate restTemplate = new RestTemplate();

  /*
   * Questo metodo chiama in rest template con una post
   * ed invia un TransactionPojo settando id e status della
   * transazione.
   */
  @Override
  @Async
  public void notify(Integer transactionId, String status) {

    final TransactionPojo transactionPojo = new TransactionPojo(transactionId.toString(), status);

    this.restTemplate.postForEntity(transactionPaypalUrl
        + "updateTransaction", transactionPojo, Void.class);
  }
}


