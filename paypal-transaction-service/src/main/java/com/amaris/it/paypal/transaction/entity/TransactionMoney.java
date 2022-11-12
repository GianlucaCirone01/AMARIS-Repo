package com.amaris.it.paypal.transaction.entity;

import com.amaris.it.paypal.messages.model.TransactionPojo;

//@Entity
public class TransactionMoney {

  private Long id;
  private String user1;
  private String user2;
  private Double money;
  private TransactionPojo.TransactionStatus statusTransaction;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUser1() {
    return user1;
  }

  public void setUser1(String user1) {
    this.user1 = user1;
  }

  public String getUser2() {
    return user2;
  }

  public void setUser2(String user2) {
    this.user2 = user2;
  }

  public Double getMoney() {
    return money;
  }

  public void setMoney(Double money) {
    this.money = money;
  }

  public TransactionPojo.TransactionStatus getStatusTransaction() {
    return statusTransaction;
  }

  public void setStatusTransaction(TransactionPojo.TransactionStatus statusTransaction) {
    this.statusTransaction = statusTransaction;
  }

}
