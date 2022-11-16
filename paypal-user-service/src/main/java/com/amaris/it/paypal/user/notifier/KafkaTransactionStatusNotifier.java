package com.amaris.it.paypal.user.notifier;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.user.producer.MessageProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class KafkaTransactionStatusNotifier implements MessageProducer {

  private static final Logger LOGGER = Logger
      .getLogger(KafkaTransactionStatusNotifier.class.getName());

  @Autowired
  private KafkaTemplate<String, TransactionResult> kafkaTemplate;

  @Override
  public void sendMessage(String topicName, Long transactionId,
      TransactionResult.TransactionStatus status) {

    final TransactionResult transactionPojo = new TransactionResult(transactionId, status);
    final String message = String.valueOf(transactionPojo);

    final ListenableFuture<SendResult<String, TransactionResult>> future =
        kafkaTemplate.send(topicName, transactionPojo);

    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, TransactionResult> result) {
        LOGGER.info(String.format("Notified transaction status: %s",
            transactionPojo));
      }

      @Override
      public void onFailure(Throwable e) {
        LOGGER.log(Level.SEVERE, "Unable to send message=["
            + message + "] due to : ", e.getMessage());
      }
    });
  }
}
