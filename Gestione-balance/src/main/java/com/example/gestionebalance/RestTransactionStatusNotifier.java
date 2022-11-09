package com.example.gestionebalance;

import com.example.paypal_model.TransactionPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTransactionStatusNotifier implements TransactionStatusNotifier {

    @Value("${transaction_paypal.url}")
    private String transaction_paypalUrl;
    private RestTemplate restTemplate = new RestTemplate();

    /*
    * Questo metodo chiama in rest template con una post
    * ed invia un TransactionPojo settando id e status della
    * transazione.
    */
    @Override
    @Async
    public Void notify(Integer transactionId, String status) {

        //System.out.println("sono qui1");
        //Map<String, String> map = new HashMap<>();
        //map.put("transaction_id", String.valueOf(transactionId));
        //map.put("status", status);

        //String requestJson = String.format("{\"transaction_id\":\"%d\",\"status\":\"%s\"}", transactionId, status);
        //System.out.println(map);
        TransactionPojo transactionPojo = new TransactionPojo(transactionId.toString(),status);

        this.restTemplate.postForEntity(transaction_paypalUrl+"updateTransaction",transactionPojo, Void.class);
        System.out.println("ho inviato il post con rest template");

        return null;
    }
}


