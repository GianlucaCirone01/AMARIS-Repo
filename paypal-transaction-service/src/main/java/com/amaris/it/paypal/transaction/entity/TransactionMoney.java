package com.amaris.it.paypal.transaction.entity;

//@Entity
public class TransactionMoney {
  public enum Status {
    PENDING,
    COMPLETE,
    CREATED,
    ERROR
  }

  private Long id;
  private String user1;
  private String user2;
  private Double money;
  private Status statusTransaction;

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

  public Status getStatusTransaction() {
    return statusTransaction;
  }

  public void setStatusTransaction(Status statusTransaction) {
    this.statusTransaction = statusTransaction;
  }

}
