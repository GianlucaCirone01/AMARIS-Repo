package com.amaris.it.paypal.messages.model;


import java.util.Objects;
import java.util.StringJoiner;

/**
 * The type TransactionRequest.
 */
public class TransactionRequest {

  private Long transactionId;
  private Long senderUserId;
  private Long receiverUserId;
  private Double amount;


  public TransactionRequest() {
  }

  public TransactionRequest(Long transactionId, Long senderUserId, Long receiverUserId,
      Double amount) {
    this.transactionId = transactionId;
    this.senderUserId = senderUserId;
    this.receiverUserId = receiverUserId;
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionRequest that = (TransactionRequest) o;
    return Objects.equals(transactionId, that.transactionId) && Objects.equals(senderUserId, that.senderUserId) && Objects.equals(receiverUserId, that.receiverUserId) && Objects.equals(amount, that.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, senderUserId, receiverUserId, amount);
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(Long transactionId) {
    this.transactionId = transactionId;
  }

  public Long getSenderUserId() {
    return senderUserId;
  }

  public void setSenderUserId(Long senderUserId) {
    this.senderUserId = senderUserId;
  }

  public Long getReceiverUserId() {
    return receiverUserId;
  }

  public void setReceiverUserId(Long receiverUserId) {
    this.receiverUserId = receiverUserId;
  }


  @Override
  public String toString() {
    return new StringJoiner(", ", TransactionRequest.class.getSimpleName() + "[", "]")
        .add("transactionId=" + transactionId)
        .add("senderUserId=" + senderUserId)
        .add("receiverUserId=" + receiverUserId)
        .add("amount=" + amount)
        .toString();
  }
}
