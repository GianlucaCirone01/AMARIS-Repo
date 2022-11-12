package com.amaris.it.paypal.messages.model;

public class TransactionPojo {

  public enum TransactionStatus {
    PENDING,
    COMPLETE,
    CREATED,
    ERROR
  }

  private Long transactionId;

  private TransactionStatus status;

  public TransactionPojo() {
  }

  public TransactionPojo(Long transactionId, TransactionStatus status) {
    this.transactionId = transactionId;
    this.status = status;
  }

  public Long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(Long transactionId) {
    this.transactionId = transactionId;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public void setStatus(TransactionStatus status) {
    this.status = status;
  }

}
