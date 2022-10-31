package com.amaris.TransactionPayPal.dao;

import com.amaris.TransactionPayPal.dto.TransactionMoneyDto;
import com.amaris.dto.TransferDto;
import com.amaris.TransactionPayPal.model.TransactionMoneyE;

import java.util.List;

public interface TransactionMoneyDAO {

    TransactionMoneyDto save(TransactionMoneyDto dto);
    List<TransactionMoneyDto> getAll();
    TransactionMoneyDto getById(int id);
    TransferDto getIdByUsername(String username);
    //void callPayPalDemoController(TransactionMoneyDto transactionMoneyDto);
}
