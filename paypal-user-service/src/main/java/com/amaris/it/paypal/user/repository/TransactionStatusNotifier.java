package com.amaris.it.paypal.user.repository;

public interface TransactionStatusNotifier {

  void notify(Integer transactionId, String status);
}
