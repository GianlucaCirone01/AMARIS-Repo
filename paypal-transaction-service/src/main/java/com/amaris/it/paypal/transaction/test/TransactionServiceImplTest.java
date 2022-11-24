package com.amaris.it.paypal.transaction.test;

import com.amaris.it.paypal.transaction.repository.TransactionRepository;
import com.amaris.it.paypal.transaction.service.TransactionServiceImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

  @Mock
  private TransactionRepository transactionRepository;

  private TransactionServiceImpl transactionService;


  @BeforeEach
  void setUp() {
    transactionService = new TransactionServiceImpl(transactionRepository);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void canCreateTransactiion() {
  }
}