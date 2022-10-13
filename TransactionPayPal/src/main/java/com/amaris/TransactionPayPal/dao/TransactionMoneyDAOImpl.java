package com.amaris.TransactionPayPal.dao;

import com.amaris.TransactionPayPal.model.TransactionMoneyE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionMoneyDAOImpl implements TransactionMoneyDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public TransactionMoneyE save(TransactionMoneyE tmE) {
        return null;
    }

    public List<TransactionMoneyE> getAll() {
        return jdbcTemplate.query("SELECT transaction FROM transaction.transaction_table", new BeanPropertyRowMapper<TransactionMoneyE>(TransactionMoneyE.class));
    }

    public TransactionMoneyE getById(int id) {
        return null;
    }
}
