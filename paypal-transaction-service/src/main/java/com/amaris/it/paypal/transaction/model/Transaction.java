package com.amaris.it.paypal.transaction.model;

import com.amaris.it.paypal.messages.model.TransactionResult;

import java.sql.Timestamp;

public class Transaction {

  private Long id;
  private String sender;
  private String receiver;
  private Double amount;
  private TransactionResult.TransactionStatus transactionStatus;
  private Timestamp creationDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
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

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "id=" + id +
        ", sender='" + sender + '\'' +
        ", receiver='" + receiver + '\'' +
        ", amount=" + amount +
        ", transactionStatus=" + transactionStatus +
        ", creationDate=" + creationDate +
        '}';
  }
}
