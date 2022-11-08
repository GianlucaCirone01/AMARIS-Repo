package com.example.gestionebalance;

public interface TransactionStatusNotifier {

    Void notify(Integer transactionId,String status);
}
