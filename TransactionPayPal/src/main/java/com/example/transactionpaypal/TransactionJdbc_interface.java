package com.example.transactionpaypal;

public interface TransactionJdbc_interface {


    Integer save(TransactionMoney dto);
    TransactionMoney findById(Integer id);
    Void updateStatus(Integer id, String status);
}
