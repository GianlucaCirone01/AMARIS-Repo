package com.example.transactionpaypal;

import com.example.paypal_model.TransactionPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path="/transactionpaypal")
public class Controller {

    @Autowired
    private TransactionService transactionService;


    @RequestMapping(path="/{user1}/{user2}/{saldo}")
    @ResponseBody
    public Void userUserSaldo (@PathVariable String user1, @PathVariable String user2, @PathVariable Float saldo)  {

        transactionService.returnTransaction(user1,user2,saldo);
        log.info(String.format("Richiesta di Transazione avvenuta"));

        return null;
    }

    @PostMapping("/updateTransaction")
    public void updateStatus (@RequestBody TransactionPojo transaction) {

        transactionService.updateStatus(transaction);
        log.info(String.format("Update Status: Id : %s , Status: %s",transaction.getTransaction_id(),transaction.getStatus()));

    }

}
