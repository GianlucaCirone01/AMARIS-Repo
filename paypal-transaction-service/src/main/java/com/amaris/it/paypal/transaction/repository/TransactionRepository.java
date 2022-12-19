package com.amaris.it.paypal.transaction.repository;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.model.ScheduledTransaction;
import com.amaris.it.paypal.transaction.model.Transaction;

import java.sql.Timestamp;
import java.util.List;

public interface TransactionRepository {

  Long save(Transaction dto);

  Long saveScheduled(ScheduledTransaction dto);

  Transaction findById(Long id);

  void updateStatus(Long id, TransactionResult.TransactionStatus status);

  List<Transaction> selectForADate(Timestamp now, TransactionResult.TransactionStatus status);

  List<Transaction> selectByStatus(TransactionResult.TransactionStatus status);

  List<Transaction> selectByStatusAndCreationDate(TransactionResult.TransactionStatus status,
      Timestamp threshold);

  List<ScheduledTransaction> selectByMode(TransactionResult.TransactionStatus status, Integer mode);

}
