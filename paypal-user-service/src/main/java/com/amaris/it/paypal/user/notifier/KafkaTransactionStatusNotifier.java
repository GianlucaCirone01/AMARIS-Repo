package com.amaris.it.paypal.user.notifier;

import com.amaris.it.paypal.messages.model.TransactionResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@Component
@ConditionalOnProperty(value = "deploy.notifier", havingValue = "kafka")
public class KafkaTransactionStatusNotifier implements TransactionStatusNotifier {

  private static final Logger LOGGER = Logger
      .getLogger(KafkaTransactionStatusNotifier.class.getName());

  private static final String TOPIC = "Transaction";

  @Autowired
  private KafkaTemplate<String, TransactionResult> transactionKafkaTemplate;

  @Override
  public void notify(Long transactionId, TransactionResult.TransactionStatus status) {

    final TransactionResult transactionPojo = new TransactionResult(transactionId, status);
    transactionKafkaTemplate.send(TOPIC, transactionPojo);

    /*final ListenableFuture<SendResult<String, TransactionResult>> future =
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

     */
  }


}
