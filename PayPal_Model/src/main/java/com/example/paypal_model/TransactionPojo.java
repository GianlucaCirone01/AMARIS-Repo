package com.example.paypal_model;

public class TransactionPojo {

    private String transaction_id;

    private String status;


    public TransactionPojo() {
    }
    public TransactionPojo(String transaction_id, String status) {
        this.transaction_id = transaction_id;
        this.status = status;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
