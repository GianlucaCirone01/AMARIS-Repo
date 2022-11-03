package com.example.transactionpaypal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class TransactionJdbc_Repository implements TransactionJdbc_interface{


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public TransactionMoney save(TransactionMoney dto) {

        jdbcTemplate.update("INSERT INTO  personaldb.transaction_money VALUES(?, ?, ?, ?, ?)",null, dto.getUser1(),dto.getUser2(),dto.getSaldo(),dto.getStato_transazione().name());

        return dto;
    }


}
