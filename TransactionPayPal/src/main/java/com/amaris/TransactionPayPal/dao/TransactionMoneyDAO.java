package com.amaris.TransactionPayPal.dao;

import com.amaris.TransactionPayPal.model.TransactionMoneyE;

import java.util.List;

public interface TransactionMoneyDAO {

    TransactionMoneyE save(TransactionMoneyE tmE);
    List<TransactionMoneyE> getAll();
    TransactionMoneyE getById(int id);
}
