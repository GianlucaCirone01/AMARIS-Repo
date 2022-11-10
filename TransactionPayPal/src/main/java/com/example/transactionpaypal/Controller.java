package com.example.transactionpaypal;

import com.example.paypal_model.TransactionPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/transactionpaypal")
public class Controller {

  @Autowired
  private TransactionService transactionService;

  private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

  @RequestMapping(path = "/{user1}/{user2}/{saldo}")
  @ResponseBody
  public Void userUserSaldo(@PathVariable String user1, @PathVariable String user2,
      @PathVariable Float saldo) {

    transactionService.returnTransaction(user1, user2, saldo);
    LOGGER.log(Level.parse("INFO"), "Richiesta di transazione avvenuta");

    return null;
  }

  @PostMapping("/updateTransaction")
  public void updateStatus(@RequestBody TransactionPojo transaction) {

    transactionService.updateStatus(transaction);
    LOGGER.log(Level.parse("INFO"),
        String.format("Update status transazione: Id: %s , Status: %s",
            transaction.getTransactionId(), transaction.getStatus()));
  }

}
