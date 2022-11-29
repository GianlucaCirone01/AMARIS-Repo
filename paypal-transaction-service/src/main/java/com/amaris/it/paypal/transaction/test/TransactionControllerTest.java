package com.amaris.it.paypal.transaction.test;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.model.Transaction;
import com.amaris.it.paypal.transaction.service.TransactionService;
import com.amaris.it.paypal.transaction.service.TransactionServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TransactionControllerTest {


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
