package com.amaris.it.paypal.messages.model;

/**
 * The type TransactionRequest.
 */
public class TransactionRequest {

  private Integer transactionId;
  private Integer senderUserId;
  private Integer receiverUserId;
  private Double amount;

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Integer getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(Integer transactionId) {
    this.transactionId = transactionId;
  }

  public Integer getSenderUserId() {
    return senderUserId;
  }

  public void setSenderUserId(Integer senderUserId) {
    this.senderUserId = senderUserId;
  }

  public Integer getReceiverUserId() {
    return receiverUserId;
  }

  public void setReceiverUserId(Integer receiverUserId) {
    this.receiverUserId = receiverUserId;
  }
}
