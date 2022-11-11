package com.example.transactionpaypal.repository;

import com.example.transactionpaypal.entity.TransactionMoney;

public interface TransactionJdbcInterface {

  Integer save(TransactionMoney dto);

  TransactionMoney findById(Integer id);

  void updateStatus(Integer id, String status);
}
