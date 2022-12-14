package com.amaris.it.paypal.transaction.repository;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.model.Transaction;

import java.sql.Date;
import java.util.List;

public interface TransactionRepository {

  Long save(Transaction dto);

  Transaction findById(Long id);

  void updateStatus(Long id, TransactionResult.TransactionStatus status);

  List<Transaction> selectForADate(Date now, TransactionResult.TransactionStatus status);
}
