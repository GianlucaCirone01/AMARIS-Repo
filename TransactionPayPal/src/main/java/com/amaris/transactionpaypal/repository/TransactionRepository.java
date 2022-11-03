package com.amaris.transactionpaypal.repository;

import com.amaris.paypalmodel.model.TransferBalance;
import com.amaris.transactionpaypal.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {


}
