package com.amaris.transactionpaypal.controller;


import com.amaris.paypalmodel.model.TransactionPojo;
import com.amaris.transactionpaypal.entity.Transaction;
import com.amaris.transactionpaypal.model.TransferMoney;

import com.amaris.transactionpaypal.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;



    @GetMapping("userTransaction/getIdByUsername/{username}")
    public int getIdByUsername(@PathVariable("username") String username){return transactionService.getIdByUsername(username);}

    @PostMapping("/saveTransaction")
    public Transaction saveTransaction(@RequestBody Transaction transaction){return transactionService.saveTransaction(transaction);}
    @GetMapping("/transaction")
    public List<Transaction> getAllTransaction(){
        return transactionService.getAllTransaction();
    }

    @GetMapping("/findOne/{id}")
    public Transaction findOne(@PathVariable("id") int id){
        return transactionService.findOne(id);
    }

    @GetMapping("/findStatusById/{id}")
    public String findStatusById(@PathVariable("id") int id){
        return transactionService.findStatusById(id);
    }

    @PostMapping("userTransaction/user/transfermoney")
    public void transferMoneyCallRestTemplate(@RequestBody TransferMoney transferMoney) {transactionService.transferMoneyCallRestTemplate(transferMoney);}

    @PostMapping("/updateTransaction")
    public void updateStatus(@RequestBody TransactionPojo transaction) {transactionService.updateStatus(transaction);}

}
