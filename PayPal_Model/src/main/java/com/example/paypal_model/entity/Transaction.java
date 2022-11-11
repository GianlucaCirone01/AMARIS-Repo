package com.example.paypal_model.entity;


public class Transaction {

  private Integer idTransaction;
  private Integer idStart;
  private Integer idEnd;

  private Float money;

  public Float getMoney() {
    return money;
  }

  public void setMoney(Float money) {
    this.money = money;
  }

  public Integer getIdTransaction() {
    return idTransaction;
  }

  public void setIdTransaction(Integer idTransaction) {
    this.idTransaction = idTransaction;
  }

  public Integer getIdStart() {
    return idStart;
  }

  public void setIdStart(Integer idStart) {
    this.idStart = idStart;
  }

  public Integer getIdEnd() {
    return idEnd;
  }

  public void setIdEnd(Integer idEnd) {
    this.idEnd = idEnd;
  }
}
