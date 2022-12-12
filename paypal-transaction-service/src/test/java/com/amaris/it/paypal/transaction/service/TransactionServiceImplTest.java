package com.amaris.it.paypal.transaction.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.connector.RestUserServiceConnector;
import com.amaris.it.paypal.transaction.connector.UserServiceConnector;
import com.amaris.it.paypal.transaction.model.Transaction;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {


  @Mock
  private UserServiceConnector userServiceConnector;

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionServiceImpl transactionServiceImpl;
  @InjectMocks
  private RestUserServiceConnector restUserServiceConnector;


  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
  }


  @Test
  public void canSaveTransaction() {
    Transaction transaction =
        new Transaction(null, "Nino", "Nuni", 1.0,
            TransactionResult.TransactionStatus.CREATED);

    when(transactionRepository.save(transaction)).thenReturn(null);

    transactionServiceImpl.createTransaction(transaction.getSenderUsername(),
        transaction.getReceiverUsername(), transaction.getAmount());

    verify(transactionRepository).save(eq(transaction));

  }

  @Test
  public void canRequestTransaction() {
    TransactionRequest transaction =
        new TransactionRequest(1L, 2L, 3L, 1.0);
    doNothing().when(userServiceConnector).requestTransaction(transaction);
    userServiceConnector.requestTransaction(transaction);
    verify(userServiceConnector).requestTransaction(transaction);

  }


  @Test
  public void canUpdateStatus() {

    TransactionResult transactionResult =
        new TransactionResult(1L,
            TransactionResult.TransactionStatus.CREATED);
    
    doNothing().when(transactionRepository).updateStatus(transactionResult.getTransactionId(),
        transactionResult.getStatus());

    transactionRepository.updateStatus(transactionResult.getTransactionId(),
        transactionResult.getStatus());

    verify(transactionRepository).updateStatus(transactionResult.getTransactionId(),
        transactionResult.getStatus());
  }
}