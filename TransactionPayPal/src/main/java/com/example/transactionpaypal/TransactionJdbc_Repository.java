package com.example.transactionpaypal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class TransactionJdbc_Repository implements TransactionJdbc_interface{


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer save(TransactionMoney dto) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql= "INSERT INTO  personaldb.transaction_money (mittente,destinatario,Money,Stato_transazione) VALUES(?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getUser1());
            ps.setString(2, dto.getUser2());
            ps.setFloat(3, dto.getSaldo());
            ps.setString(4,dto.getStato_transazione().name());
            return ps;
        }, keyHolder);
        //jdbcTemplate.update(sql , dto.getUser1(),dto.getUser2(),dto.getSaldo(),dto.getStato_transazione().name());

        return keyHolder.getKey().intValue();
        //return dto.getId();
    }

    @Override
    public TransactionMoney findById(Integer id){

        String sql = "SELECT * FROM personaldb.transaction_money WHERE id=?";

        return (TransactionMoney) jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<TransactionMoney>(TransactionMoney.class),id);
        //return jdbcTemplate.queryForObject(sql, TransactionMoney.class,id);

    }

    @Override
    public Void updateStatus(Integer id, String status){
        String sql = "UPDATE personaldb.transaction_money SET Stato_transazione = ? WHERE ID = ?";

        jdbcTemplate.update(sql,status,id);
        return null;
    }

}
