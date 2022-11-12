package com.amaris.it.paypal.transaction.repository;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.model.TransactionMoney;

public interface TransactionJdbcInterface {

  Long save(TransactionMoney dto);

  TransactionMoney findById(Long id);

  void updateStatus(Long id, TransactionResult.TransactionStatus status);
}
