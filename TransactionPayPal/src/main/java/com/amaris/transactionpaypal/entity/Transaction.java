package com.amaris.transactionpaypal.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "transaction")
@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private int id;
  @Column(name = "username_mittente")
  private String usernameMittente;
  @Column(name = "username_destinatario")
  private String usernameDestinatario;
  @Column(name = "balance")
  private int balance;

  public enum StatoTransizione {
    pending,
    completed,
    error,
    created
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "stato_transizione")
  private StatoTransizione statoTransizione;

  public Transaction(String username, String username1, int balance,
      StatoTransizione statoTransizione) {
    this.usernameMittente = username;
    this.usernameDestinatario = username1;
    this.balance = balance;
    this.statoTransizione = statoTransizione;
  }

  public void setStatoTransizione(StatoTransizione statoTransizione) {
    this.statoTransizione = statoTransizione;
  }
}