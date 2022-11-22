package com.amaris.it.paypal.user.notifier;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TransactionStatusNotifier {

  void notify(Long transactionId,
      TransactionResult.TransactionStatus status) throws JsonProcessingException;
}
