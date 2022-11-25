package com.amaris.it.paypal.transaction.test;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.connector.UserServiceConnector;
import com.amaris.it.paypal.transaction.model.Transaction;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;
import com.amaris.it.paypal.transaction.service.TransactionServiceImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class TransactionServiceImplTest {

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private UserServiceConnector userServiceConnector;

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionServiceImpl transactionService;

  @Value("${user_service.url}")
  private String userServiceUrl;


  @BeforeEach
  void setUp() {
    transactionService = new TransactionServiceImpl(transactionRepository);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void canCreateTransactiion() {

    String usernameSender = "nino";
    String usernameReciver = "nino2";
    long usernameSenderId = 1;
    long usernameReciverId = 2;

    when(userServiceConnector.getIdByUsername(usernameSender)).thenReturn(usernameSenderId);
    when(userServiceConnector.getIdByUsername(usernameReciver)).thenReturn(usernameReciverId);

    Transaction transaction =
        new Transaction(1L, usernameSender, usernameReciver,
            1.0, TransactionResult.TransactionStatus.CREATED);

    when(transactionRepository.save(transaction)).thenReturn(transaction.getTransactionId());

    TransactionRequest transactionRequest =
        new TransactionRequest(transaction.getTransactionId(), 2L, 3L, 1.0);

    transactionService.createTransaction(usernameSender, usernameReciver, transactionRequest.getAmount());


    Assertions.assertEquals(transactionRequest.getTransactionId(), transaction.getTransactionId());
  }
}