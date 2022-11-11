package com.amaris.paypal.model;

public interface TransactionStatusNotifier {

  void notify(int transactionId, String status);
}
