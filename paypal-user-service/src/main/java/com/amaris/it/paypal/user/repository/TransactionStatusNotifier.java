package com.amaris.it.paypal.user.repository;

public interface TransactionStatusNotifier {

  void notify(Long transactionId, String status);
}
