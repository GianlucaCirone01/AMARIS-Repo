package com.amaris.it.paypal.user.service;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.user.repository.TransactionStatusNotifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTransactionStatusNotifier implements TransactionStatusNotifier {

  @Value("${transaction_paypal.url}")
  private String transactionPaypalUrl;
  private final RestTemplate restTemplate = new RestTemplate();

  /**
   * Questo metodo chiama in rest template con una post
   * ed invia un TransactionResult settando id e status della
   * transazione.
   */
  @Override
  @Async
  public void notify(Long transactionId, TransactionResult.TransactionStatus status) {

    final TransactionResult transactionPojo = new TransactionResult(transactionId, status);

    this.restTemplate.postForEntity(transactionPaypalUrl
        + "updateTransaction", transactionPojo, Void.class);
  }

}


