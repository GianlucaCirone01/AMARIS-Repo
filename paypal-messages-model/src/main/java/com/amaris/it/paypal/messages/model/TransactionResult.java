package com.amaris.it.paypal.messages.model;

import java.util.Objects;
import java.util.StringJoiner;

public class TransactionResult {

  public enum TransactionStatus {
    PENDING,
    COMPLETE,
    CREATED,
    ERROR
  }

  private Long transactionId;

  private TransactionStatus status;

  public TransactionResult() {
  }

  public TransactionResult(Long transactionId, TransactionStatus status) {
    this.transactionId = transactionId;
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionResult that = (TransactionResult) o;
    return Objects.equals(transactionId, that.transactionId) && status == that.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, status);
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

  @Override
  public String toString() {
    return new StringJoiner(", ", TransactionResult.class.getSimpleName() + "[", "]")
        .add("transactionId=" + transactionId)
        .add("status=" + status)
        .toString();
  }
}
