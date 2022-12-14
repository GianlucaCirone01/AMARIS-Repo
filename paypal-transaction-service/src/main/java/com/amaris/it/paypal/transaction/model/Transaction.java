package com.amaris.it.paypal.transaction.model;

import com.amaris.it.paypal.messages.model.TransactionResult;

import java.sql.Timestamp;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

  private Long transactionId;
  private String senderUsername;
  private String receiverUsername;
  private Double amount;
  private TransactionResult.TransactionStatus transactionStatus;
  private Timestamp executionDate;

  public Timestamp getExecutionDate() {
    return executionDate;
  }

  public void setExecutionDate(Timestamp executionDate) {
    this.executionDate = executionDate;
  }


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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction that = (Transaction) o;
    return Objects.equals(transactionId, that.transactionId) && Objects.equals(senderUsername, that.senderUsername) && Objects.equals(receiverUsername, that.receiverUsername) && Objects.equals(amount, that.amount) && transactionStatus == that.transactionStatus;
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, senderUsername, receiverUsername, amount, transactionStatus);
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "transactionId=" + transactionId +
        ", senderUsername='" + senderUsername + '\'' +
        ", receiverUsername='" + receiverUsername + '\'' +
        ", amount=" + amount +
        ", transactionStatus=" + transactionStatus +
        ", executionDate=" + executionDate +
        '}';
  }
}
