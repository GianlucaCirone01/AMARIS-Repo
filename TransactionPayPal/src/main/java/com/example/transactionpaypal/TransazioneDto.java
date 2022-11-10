package com.example.transactionpaypal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransazioneDto {
  public enum Stato {PENDING, COMPLETE, NOT_COMPLETE}

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String user1;
  private String user2;
  private Float saldo;

  private Stato statoTransazione = Stato.PENDING;

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

  public Float getSaldo() {
    return saldo;
  }

  public void setSaldo(Float saldo) {
    this.saldo = saldo;
  }

  public Stato getStatoTransazione() {
    return statoTransazione;
  }

  public void setStatoTransazione(Stato statoTransazione) {
    this.statoTransazione = statoTransazione;
  }

}
