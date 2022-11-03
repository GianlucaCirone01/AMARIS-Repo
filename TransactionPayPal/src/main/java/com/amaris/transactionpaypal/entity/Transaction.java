package com.amaris.transactionpaypal.entity;


import lombok.*;

import javax.persistence.*;

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
    @Column(name="stato_transizione")
    private StatoTransizione statoTransizione;

    public Transaction(String username, String username1, int balance, StatoTransizione statoTransizione) {
        this.usernameMittente = username;
        this.usernameDestinatario = username1;
        this.balance = balance;
        this.statoTransizione= statoTransizione;

    }

    public Transaction setStatoTransizione(StatoTransizione statoTransizione) {
        this.statoTransizione = statoTransizione;
        return null;
    }
}