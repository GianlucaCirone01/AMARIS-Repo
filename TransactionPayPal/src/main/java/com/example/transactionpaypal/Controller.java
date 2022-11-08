package com.example.transactionpaypal;

import com.example.paypal_model.TransactionPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/transactionpaypal")
public class Controller {

    @Autowired
    private TransactionService transactionService;


    @RequestMapping(path="/{user1}/{user2}/{saldo}")
    @ResponseBody
    public Void userUserSaldo(@PathVariable String user1, @PathVariable String user2, @PathVariable Float saldo)  {

        return transactionService.returnTransaction(user1,user2,saldo);
    }

    @PostMapping("/updateTransaction")
    public void updateStatus(@RequestBody TransactionPojo transaction) {
        transactionService.updateStatus(transaction);

    }

}
