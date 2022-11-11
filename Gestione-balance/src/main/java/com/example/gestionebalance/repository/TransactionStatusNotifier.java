package com.example.gestionebalance.repository;

public interface TransactionStatusNotifier {

  void notify(Integer transactionId, String status);
}
