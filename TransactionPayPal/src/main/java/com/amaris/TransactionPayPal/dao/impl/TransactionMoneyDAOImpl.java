package com.amaris.TransactionPayPal.dao.impl;

import com.amaris.TransactionPayPal.ENUM.Transaction_status;
import com.amaris.TransactionPayPal.dao.TransactionMoneyDAO;
import com.amaris.TransactionPayPal.dto.TransactionMoneyDto;
import com.amaris.TransactionPayPal.model.TransactionMoneyE;
import com.amaris.dto.TransferDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Repository
public class TransactionMoneyDAOImpl implements TransactionMoneyDAO {

    final static Logger log = Logger.getLogger(TransactionMoneyDAOImpl.class.getName());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private ModelMapper mapper;

    @Override
    public TransactionMoneyDto save(TransactionMoneyDto dto) {
        TransactionMoneyE entity=this.mapper.map(dto, TransactionMoneyE.class);
        System.out.println("OGETTO TRASFORMATO"+entity);
        entity.setTransaction_status(Transaction_status.PENDING);

        jdbcTemplate.update("INSERT INTO transaction.transaction_table(sender_username, recipient_username, balance, transaction_status) VALUES(?, ?, ?, ?)",
                dto.getSender_username(), dto.getRecipient_username(), dto.getBalance(), entity.getTransaction_status().name());
        return this.mapper.map(entity, TransactionMoneyDto.class);
    }

    @Override
    public List<TransactionMoneyDto> getAll() {
        List<TransactionMoneyE> entities=jdbcTemplate.query("SELECT * FROM transaction.transaction_table",
                new BeanPropertyRowMapper<TransactionMoneyE>(TransactionMoneyE.class));
        List<TransactionMoneyDto> dtoList=entities.stream()
                .map((TransactionMoneyE t)->{
                return this.mapper.map(t, TransactionMoneyDto.class);
        }).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public TransactionMoneyDto getById(int id) {
        TransactionMoneyDto dto= null;
        TransactionMoneyE result= jdbcTemplate.queryForObject("SELECT * FROM transaction.transaction_table WHERE transaction_id=?",
                new BeanPropertyRowMapper<TransactionMoneyE>(TransactionMoneyE.class),id);
        dto=this.mapper.map(result, TransactionMoneyDto.class);
        return dto;
    }

    @Override
    public TransferDto getIdByUsername(String username) {
        RestTemplate rt=new RestTemplate();
        String url="http://localhost:8080/api/user/";
        String url2=url+username;
        return rt.getForObject(url2,TransferDto.class);
    }
/*
    @Override
    public void callPayPalDemoController(TransactionMoneyDto transactionMoneyDto) {
        RestTemplate rt=new RestTemplate();
        TransferDto id1= getIdByUsername(transactionMoneyDto.getUsernameEmittente());
        TransferDto id2= getIdByUsername(transactionMoneyDto.getUsernameDestinatario());

        TransferDto tdo= new TransferDto(id1.getId1(),id2.getId1(),transactionMoneyDto.getBalance());
        TransactionMoneyE transactionMoneyE= new TransactionMoneyE(transactionMoneyDto.getUsernameEmittente(),
         transactionMoneyDto.getUsernameDestinatario(), transactionMoneyDto.getBalance());

        String url="http://localhost:8080/api/user/balanceTransaction";

        rt.postForEntity(url, tdo, Void.class).getBody();

        transactionMoneyE.setTransaction_status(Transaction_status.PENDING);
        save(transactionMoneyE);
    }
   */

}
