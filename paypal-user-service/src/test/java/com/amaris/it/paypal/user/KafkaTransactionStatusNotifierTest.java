package com.amaris.it.paypal.user;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.user.notifier.KafkaTransactionStatusNotifier;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.logging.Logger;

@RunWith(MockitoJUnitRunner.class)
public class KafkaTransactionStatusNotifierTest {

  @Mock
  KafkaTemplate<String, Object> kafkaTemplate;

  @InjectMocks
  KafkaTransactionStatusNotifier kafkaTransactionStatusNotifier;

  private static final Logger LOGGER = Logger
      .getLogger(KafkaTransactionStatusNotifier.class.getName());

  @Test
  public void notifyTest() throws JsonProcessingException {

/*
    final String topic = "Transaction";
    final TransactionResult transactionPojo = new TransactionResult(1L, TransactionResult.TransactionStatus.COMPLETE);

    SettableListenableFuture<SendResult<String, Object>> future = new SettableListenableFuture<>();
    when(kafkaTemplate.send(topic, transactionPojo)).thenReturn(future);

    kafkaTransactionStatusNotifier.notify(transactionPojo.getTransactionId(), transactionPojo.getStatus());

    //verify(future).addCallback(any());

 */
    SendResult sendResult = mock(SendResult.class);
    final String topic = "Transaction";
    final TransactionResult transactionPojo = new TransactionResult(1L,
        TransactionResult.TransactionStatus.COMPLETE);
    SettableListenableFuture<SendResult<String, Object>> future = new SettableListenableFuture<>();
    future.set(sendResult);

    when(kafkaTemplate.send(eq(topic), eq(transactionPojo))).thenReturn(future);

    kafkaTransactionStatusNotifier.notify(transactionPojo.getTransactionId(), transactionPojo.getStatus());

    verify(kafkaTemplate).send(eq(topic), eq(transactionPojo));


  }

}
