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
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestTransactionStatusNotifierTest {


  @Mock
  RestTemplate restTemplate;

  @InjectMocks
  RestTransactionStatusNotifier restTransactionStatusNotifier;

  @Test
  public void NotifyTest() {

    TransactionResult transactionPojo = new TransactionResult(1L,
        TransactionResult.TransactionStatus.COMPLETE);

    TransactionResult transactionResult = new TransactionResult(1L,
        TransactionResult.TransactionStatus.COMPLETE);

    String transactionPaypalUrl = "http://localhost:8081/transactionpaypal/";
    ResponseEntity<Void> response = new ResponseEntity(transactionPojo, HttpStatus.OK);

    when(restTemplate.postForEntity(transactionPaypalUrl
        + "updateTransaction", transactionPojo, Void.class)).thenReturn(response);

    restTransactionStatusNotifier.notify(transactionPojo.getTransactionId(), transactionPojo.getStatus());

  }

}
