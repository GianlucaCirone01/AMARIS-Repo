package com.example.gestionebalance;

public interface TransactionStatusNotifier {

  void notify(Integer transactionId, String status);
}
