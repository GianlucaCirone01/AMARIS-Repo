package com.amaris.paypal.paypal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utenti {
    private int id;
    private String username;
    private String nome;
    private String cognome;
    private int saldo;
}
