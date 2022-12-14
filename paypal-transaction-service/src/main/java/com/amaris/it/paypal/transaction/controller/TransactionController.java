package com.amaris.it.paypal.transaction.controller;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/transactionpaypal")
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  private static final Logger LOGGER = Logger.getLogger(TransactionController.class.getName());

  @RequestMapping(path = "/{user1}/{user2}/{balance}")
  @ResponseBody
  public void requestTransaction(@PathVariable String user1, @PathVariable String user2,
      @PathVariable Double balance) {

    transactionService.createTransaction(user1, user2, balance);
    LOGGER.log(Level.INFO, "Request for transaction made");
  }

  @RequestMapping(path = "/{user1}/{user2}/{balance}/{date}")
  @ResponseBody
  public void createTransactionForADate(@PathVariable String user1, @PathVariable String user2,
      @PathVariable Double balance,
      @PathVariable Timestamp date) throws InterruptedException {

    transactionService.createTransactionForADate(user1, user2, balance, date);
    LOGGER.log(Level.INFO, "Request for a transaction on a specific date [" + date.toString() + "] made");
  }

  @PostMapping("/updateTransaction")
  public void updateStatus(@RequestBody TransactionResult transaction) {

    transactionService.updateStatus(transaction);
    LOGGER.log(Level.INFO,
        String.format("Update status transaction: Id: %s , TransactionStatus: %s",
            transaction.getTransactionId(), transaction.getStatus()));
  }

}
