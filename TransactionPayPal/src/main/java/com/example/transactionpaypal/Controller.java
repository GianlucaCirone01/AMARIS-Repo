package com.example.transactionpaypal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/transactionpaypal")
public class Controller {

    @Autowired
    private TransactionService transactionService;

    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(path="/{user1}/{user2}/{saldo}")
    @ResponseBody
    public String userUserSaldo(@PathVariable String user1, @PathVariable String user2,@PathVariable Float saldo) throws NoSuchFieldException {

        ResponseEntity<UserUserSaldo> response = restTemplate.getForEntity("http://localhost:8080/transactionpaypal/"+ user1 +"/"+ user2 + "/"+ saldo, UserUserSaldo.class);
        return transactionService.returnTransaction(response);
    }

}
