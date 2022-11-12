package com.amaris.it.paypal.user.repository;

import com.amaris.it.paypal.messages.model.TransactionPojo;

public interface TransactionStatusNotifier {

  void notify(Long transactionId, TransactionPojo.TransactionStatus status);
}
