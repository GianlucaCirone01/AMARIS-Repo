package com.amaris.it.paypal.messages.model;

public class TransactionPojo {

  private String transactionId;

  private String status;

  public TransactionPojo() {
  }

  public TransactionPojo(String transactionId, String status) {
    this.transactionId = transactionId;
    this.status = status;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
