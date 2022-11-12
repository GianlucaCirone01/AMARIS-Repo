package com.amaris.it.paypal.transaction.repository;

import com.amaris.it.paypal.transaction.entity.TransactionMoney;

public interface TransactionJdbcInterface {

  Long save(TransactionMoney dto);

  TransactionMoney findById(Long id);

  void updateStatus(Long id, String status);
}
