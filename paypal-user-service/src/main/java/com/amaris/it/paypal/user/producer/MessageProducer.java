package com.amaris.it.paypal.user.producer;

import com.amaris.it.paypal.messages.model.TransactionResult;

public interface MessageProducer {

  void sendMessage(String topic, Long transactionId, TransactionResult.TransactionStatus status);
}
