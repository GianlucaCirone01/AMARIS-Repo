package com.amaris.it.paypal.transaction.service;

import com.amaris.it.paypal.messages.model.TransactionResult;

import java.sql.Timestamp;

public interface TransactionService {
  /**
   * Tramite restTemplate recupera gli id dei due utenti
   * per poi eseguire la transazione. Infine richiama un altro
   * metodo per completare in maniera asincrona la transazione.
   */
  void createTransaction(String user1, String user2, Double money);

  void createTransactionForADate(String user1, String user2, Double money,
      Timestamp executionDate);

  void executionTransaction();

  /**
   * Preleva l'id e l stuatus dai campi del TransactionResult
   * e richiama il metodo che ne fa il lavoro.
   */
  void updateStatus(TransactionResult transactionPojo);

  void retryTransaction();

  void abortTransaction();
}
