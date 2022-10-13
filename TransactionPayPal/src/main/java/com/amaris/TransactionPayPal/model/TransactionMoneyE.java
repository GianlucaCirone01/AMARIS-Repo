package com.amaris.TransactionPayPal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionMoneyE {
    public int id;
    public String sender_username;
    public String recipient_username;
    public Double balance;
    public Enum transaction_Satus;

}
