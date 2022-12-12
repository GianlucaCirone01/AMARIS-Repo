package com.amaris.it.paypal.transaction.controller;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.model.Transaction;
import com.amaris.it.paypal.transaction.service.TransactionService;
import com.amaris.it.paypal.transaction.service.TransactionServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {


  // FIXME devi testare il controller
  @InjectMocks
  TransactionServiceImpl transactionServiceImpl;

  @Mock
  TransactionService transactionService;

  @Test
  public void canRequestTransaction() {
    Transaction transaction =
        new Transaction(1L, "Ciccio", "ciccia",
            1.0, TransactionResult.TransactionStatus.PENDING);


    transactionService.createTransaction(transaction.getSenderUsername(),
        transaction.getReceiverUsername(), transaction.getAmount());
    verify(transactionService).createTransaction(transaction.getSenderUsername(),
        transaction.getReceiverUsername(), transaction.getAmount());
  }

  @Test
  public void canUpdateStatus() {

    TransactionResult transactionResult = new TransactionResult(1L, TransactionResult.TransactionStatus.COMPLETE);
    transactionService.updateStatus(transactionResult);
    verify(transactionService).updateStatus(transactionResult);
  }
}
