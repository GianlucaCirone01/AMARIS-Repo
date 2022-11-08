package com.example.gestionebalance;

import org.springframework.stereotype.Repository;

@Repository
public interface TransactionStatusNotifier {

    Void notify(Integer transactionId,String status);
}
