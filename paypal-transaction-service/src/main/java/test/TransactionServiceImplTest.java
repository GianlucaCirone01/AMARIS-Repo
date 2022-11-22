package test;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.repository.TransactionRepository;
import com.amaris.it.paypal.transaction.service.TransactionServiceImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class TransactionServiceImplTest {

  private AutoCloseable autoCloseable;
  @Mock
  private TransactionRepository transactionRepository;
  private TransactionServiceImpl transactionService;
  private TransactionResult transactionResult;

  @Test
  @Disabled
  void createTransaction() {
  }

  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
    transactionService = new TransactionServiceImpl(transactionRepository);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void canUpdateStatus() {

  }
}