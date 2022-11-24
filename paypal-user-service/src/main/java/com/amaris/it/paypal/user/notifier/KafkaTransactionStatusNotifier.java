package com.amaris.it.paypal.user.notifier;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@ConditionalOnProperty(value = "deploy.notifier", havingValue = "kafka")
public class KafkaTransactionStatusNotifier implements TransactionStatusNotifier {

  private static final Logger LOGGER = Logger
      .getLogger(KafkaTransactionStatusNotifier.class.getName());

  private static final String TOPIC = "Transaction";

  @Autowired
  private KafkaTemplate<String, String> transactionKafkaTemplate;

  @Override
  public void notify(Long transactionId,
      TransactionResult.TransactionStatus status) throws JsonProcessingException {

    final TransactionResult transactionPojo = new TransactionResult(transactionId, status);

    final ObjectMapper objectMapper = new ObjectMapper();
    final String jsonString = objectMapper.writeValueAsString(transactionPojo);

    final ListenableFuture<SendResult<String, String>> future =
        transactionKafkaTemplate.send(TOPIC, jsonString);

    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        LOGGER.info(String.format("Notified transaction status: %s",
            transactionPojo));
      }

      @Override
      public void onFailure(Throwable e) {
        LOGGER.log(Level.SEVERE, "Unable to send message=["
            + transactionPojo.toString() + "] due to : ", e.getMessage());
      }
    });
  }
}
