package com.amaris.TransactionPayPal.web.controller;

import com.amaris.TransactionPayPal.dao.TransactionMoneyDAO;
import com.amaris.TransactionPayPal.model.TransactionMoneyE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionMoneyController {

    @Autowired
    private TransactionMoneyDAO tmDao;

    @GetMapping("/transferMoney")
    public List<TransactionMoneyE> getAll(){
       return tmDao.getAll();
    }

}
