package com.amaris.it.paypal.transaction.entity;

//@Entity
public class TransactionMoney {
  public enum Status {
    PENDING,
    COMPLETE,
    CREATED,
    ERROR
  }

  private Integer id;
  private String user1;
  private String user2;
  private Float money;
  private Status statusTransaction;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public Float getMoney() {
    return money;
  }

  public void setMoney(Float money) {
    this.money = money;
  }

  public Status getStatusTransaction() {
    return statusTransaction;
  }

  public void setStatusTransaction(Status statusTransaction) {
    this.statusTransaction = statusTransaction;
  }

}
