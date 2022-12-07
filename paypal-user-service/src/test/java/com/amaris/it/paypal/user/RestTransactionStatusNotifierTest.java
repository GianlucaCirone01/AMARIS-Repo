package com.amaris.it.paypal.user;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.user.notifier.RestTransactionStatusNotifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RestTransactionStatusNotifierTest {

  @Mock
  RestTemplate restTemplate;

  @InjectMocks
  RestTransactionStatusNotifier restTransactionStatusNotifier;

  @Test
  public void NotifyTest() {

    // inizializzo il field che spring valorizza con @Value
    ReflectionTestUtils.setField(restTransactionStatusNotifier,
        "transactionPaypalUrl", "http://localhost:8081/transactionpaypal/");

    TransactionResult transactionPojo = new TransactionResult(1L,
        TransactionResult.TransactionStatus.COMPLETE);


    when(restTemplate.postForEntity(eq("http://localhost:8081/transactionpaypal/updateTransaction"),
        eq(transactionPojo),
        eq(Void.class)))
        .thenReturn(new ResponseEntity(transactionPojo, HttpStatus.OK));

    restTransactionStatusNotifier.notify(transactionPojo.getTransactionId(), transactionPojo.getStatus());

    verify(restTemplate, times(1))
        .postForEntity(eq("http://localhost:8081/transactionpaypal/updateTransaction"),
            eq(transactionPojo), eq(Void.class));

  }

}
