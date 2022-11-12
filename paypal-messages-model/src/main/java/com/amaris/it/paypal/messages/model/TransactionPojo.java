package com.amaris.it.paypal.messages.model;

public class TransactionPojo {

  private Long transactionId;

  private String status;

  public TransactionPojo() {
  }

  public TransactionPojo(Long transactionId, String status) {
    this.transactionId = transactionId;
    this.status = status;
  }

  public Long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(Long transactionId) {
    this.transactionId = transactionId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
