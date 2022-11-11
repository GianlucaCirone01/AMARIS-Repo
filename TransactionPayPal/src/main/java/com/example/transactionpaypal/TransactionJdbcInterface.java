package com.example.transactionpaypal;

public interface TransactionJdbcInterface {

  Integer save(TransactionMoney dto);

  TransactionMoney findById(Integer id);

  void updateStatus(Integer id, String status);
}
