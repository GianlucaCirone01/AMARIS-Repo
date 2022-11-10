package com.example.paypal_model;


public class Transaction {

  private Integer idTra;
  private Integer idStart;
  private Integer idEnd;

  private Float money;

  public Float getMoney() {
    return money;
  }

  public void setMoney(Float money) {
    this.money = money;
  }

  public Integer getIdTra() {
    return idTra;
  }

  public void setIdTra(Integer idTra) {
    this.idTra = idTra;
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
