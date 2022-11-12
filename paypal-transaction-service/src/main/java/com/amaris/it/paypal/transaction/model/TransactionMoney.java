package com.amaris.it.paypal.transaction.model;

import com.amaris.it.paypal.messages.model.TransactionResult;

public class TransactionMoney {

  private Long transactionId;
  private String senderUsername;
  private String receiverUsername;
  private Double amount;
  private TransactionResult.TransactionStatus transactionStatus;

  public Long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(Long transactionId) {
    this.transactionId = transactionId;
  }

  public String getSenderUsername() {
    return senderUsername;
  }

  public void setSenderUsername(String senderUsername) {
    this.senderUsername = senderUsername;
  }

  public String getReceiverUsername() {
    return receiverUsername;
  }

  public void setReceiverUsername(String receiverUsername) {
    this.receiverUsername = receiverUsername;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public TransactionResult.TransactionStatus getTransactionStatus() {
    return transactionStatus;
  }

  public void setTransactionStatus(TransactionResult.TransactionStatus transactionStatus) {
    this.transactionStatus = transactionStatus;
  }

}
