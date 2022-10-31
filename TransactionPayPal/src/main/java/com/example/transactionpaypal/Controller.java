package com.example.transactionpaypal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/transactionpaypal")
public class Controller {

    @Autowired
    private TransactionService transactionService;


    @RequestMapping(path="/{user1}/{user2}/{saldo}")
    @ResponseBody
    public ResponseEntity<String> userUserSaldo(@PathVariable String user1, @PathVariable String user2, @PathVariable Float saldo)  {

        return transactionService.returnTransaction(user1,user2,saldo);
    }

}
