package com.amaris.it.paypal.transaction.connector;

import com.amaris.it.paypal.messages.model.TransactionRequest;

public interface UserServiceConnector {

  Long getIdByUsername(String username);

  void requestTransaction(TransactionRequest transactionRequest);


}
