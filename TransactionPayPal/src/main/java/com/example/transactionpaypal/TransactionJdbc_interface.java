package com.example.transactionpaypal;

public interface TransactionJdbc_interface {


  Integer save(TransactionMoney dto);

  TransactionMoney findById(Integer id);

  void updateStatus(Integer id, String status);
}
