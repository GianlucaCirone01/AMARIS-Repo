package com.amaris.it.paypal.user.notifier;

import com.amaris.it.paypal.messages.model.TransactionResult;

public interface TransactionStatusNotifier {

  void notify(Long transactionId, TransactionResult.TransactionStatus status);
}
