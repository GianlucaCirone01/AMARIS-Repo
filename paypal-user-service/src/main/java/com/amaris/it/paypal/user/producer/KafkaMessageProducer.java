package com.amaris.it.paypal.user.producer;

import com.amaris.it.paypal.messages.model.TransactionResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class KafkaMessageProducer implements MessageProducer {

  private static final Logger LOGGER = Logger
      .getLogger(KafkaMessageProducer.class.getName());

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void sendMessage(String topicName, Long transactionId,
      TransactionResult.TransactionStatus status) {

    final TransactionResult transactionPojo = new TransactionResult(transactionId, status);
    final String message = String.valueOf(transactionPojo);

    final ListenableFuture<SendResult<String, String>> future =
        kafkaTemplate.send(topicName, message);

    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        LOGGER.info(String.format("Notified transaction status: %s",
            transactionPojo));
      }

      @Override
      public void onFailure(Throwable e) {
        LOGGER.log(Level.SEVERE, "Unable to send message=["
            + message + "] due to : ", e);
      }
    });
  }
}
