package com.amaris.it.paypal.transaction.repository;

import com.amaris.it.paypal.transaction.entity.TransactionMoney;

public interface TransactionJdbcInterface {

  Integer save(TransactionMoney dto);

  TransactionMoney findById(Integer id);

  void updateStatus(Integer id, String status);
}
