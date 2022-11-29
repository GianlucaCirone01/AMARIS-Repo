package com.amaris.it.paypal.user;

import com.amaris.it.paypal.user.notifier.KafkaTransactionStatusNotifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;

@RunWith(MockitoJUnitRunner.class)
public class KafkaTransactionStatusNotifierTest {

  @Mock
  KafkaTemplate kafkaTemplate;

  @InjectMocks
  KafkaTransactionStatusNotifier kafkaTransactionStatusNotifier;

  @Test
  public void notifyTest() {

  }

}
