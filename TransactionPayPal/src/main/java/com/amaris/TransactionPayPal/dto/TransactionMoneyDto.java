package com.amaris.TransactionPayPal.dto;

import com.amaris.TransactionPayPal.ENUM.Transaction_status;

public class TransactionMoneyDto {

    private int transaction_id;
    private String sender_username;
    private String recipient_username;
    private Double balance;
    public Transaction_status transaction_status;


    public int getTransaction_id() {
        return transaction_id;
    }
    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }
    public String getSender_username() {
        return sender_username;
    }
    public void setSender_username(String sender_username) {
        this.sender_username = sender_username;
    }
    public String getRecipient_username() {
        return recipient_username;
    }
    public void setRecipient_username(String recipient_username) {
        this.recipient_username = recipient_username;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public Transaction_status getTransaction_status() {
        return transaction_status;
    }
    public void setTransaction_status(Transaction_status transaction_status) {
        this.transaction_status = transaction_status;
    }
}
