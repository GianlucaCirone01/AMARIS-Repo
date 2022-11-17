package com.amaris.it.paypal.user.notifier;

import com.amaris.it.paypal.messages.model.TransactionResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
@ConditionalOnProperty(value = "deploy.notifier", havingValue = "rest")
public class RestTransactionStatusNotifier implements TransactionStatusNotifier {

  private static final Logger LOGGER = Logger
      .getLogger(RestTransactionStatusNotifier.class.getName());

  @Value("${transaction_paypal.url}")
  private String transactionPaypalUrl;
  @Autowired
  private RestTemplate restTemplate;

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
    LOGGER.info(String.format("Notified transaction status: %s",
        transactionPojo));
  }

}