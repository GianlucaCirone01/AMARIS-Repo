package com.amaris.TransactionPayPal.model;

import com.amaris.TransactionPayPal.ENUM.Transaction_status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionMoneyE {

    public int transaction_id;
    public String sender_username;
    public String recipient_username;
    public Double balance;
    public Transaction_status transaction_status;

    public TransactionMoneyE(String usernameEmittente, String usernameDestinatario, Double balance) {
    }
}
