package com.amaris.it.paypal.messages.model;

import java.util.StringJoiner;

/**
 * The type TransactionRequest.
 */

public class TransactionRequest {

  private Long transactionId;
  private Long senderUserId;
  private Long receiverUserId;
  private Double amount;

<<<<<<< HEAD
=======
  public TransactionRequest() {
  }

>>>>>>> 874cef68361bcb8bb74a6f0c44f1e870432fef68
  public TransactionRequest(Long transactionId, Long senderUserId, Long receiverUserId,
      Double amount) {
    this.transactionId = transactionId;
    this.senderUserId = senderUserId;
    this.receiverUserId = receiverUserId;
    this.amount = amount;
  }

<<<<<<< HEAD
  public TransactionRequest() {
  }

=======
>>>>>>> 874cef68361bcb8bb74a6f0c44f1e870432fef68
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
