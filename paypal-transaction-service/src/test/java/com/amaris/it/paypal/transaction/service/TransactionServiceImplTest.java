package com.amaris.it.paypal.transaction.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.connector.UserServiceConnector;
import com.amaris.it.paypal.transaction.model.Transaction;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TransactionServiceImplTest {


  @Mock
  private UserServiceConnector userServiceConnector;

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionServiceImpl transactionServiceImpl = new TransactionServiceImpl();


  @Before("")
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void canCreateTransactiion() {

    String usernameSender = "paolo";
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

    transactionServiceImpl.createTransaction(usernameSender, usernameReciver, transactionRequest.getAmount());

    // ogni test deve avere uno scopo ben preciso,
    // con l'equals o la verify finale che validino il comportamento della classe
    // in questo caso entrambi i campi sono stati decisi nel test, non ha valore aggiunto
    Assertions.assertEquals(transactionRequest.getTransactionId(), transaction.getTransactionId());

    // in questo caso essendo il metodo void,
    // vorrei verificare 3 le interazioni (magari in 3 test separati)
    // 1) che venga chiamata la save del repository
    // 2) che venga chiamata userServiceConnector.requestTransaction
    // 3) che venga chiamata updateStatus del repository

  }


  @Test
  public void canUpdateStatus() {

    TransactionResult transactionResult =
        new TransactionResult(1L,
            TransactionResult.TransactionStatus.CREATED);

    // FIXME quando testi il transaction service il metodo principale da invocare è sempre il suo
    // prima puoi decidere il comportamento dei mock all'interno (con le when)
    // dopo puoi verificare se alcuni metodi dei mock son stati chiamati (con la verify)

    transactionRepository
        .updateStatus(transactionResult.getTransactionId(), TransactionResult.TransactionStatus.PENDING);
    verify(transactionRepository).updateStatus(transactionResult.getTransactionId(),
        TransactionResult.TransactionStatus.PENDING);
  }
}