package com.example.gestionebalance;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTransactionStatusNotifier implements TransactionStatusNotifier {

    private RestTemplate restTemplate = new RestTemplate();
    @Override
    @Async
    public Void notify(Integer transactionId, String status) {

        System.out.println("sono qui1");
        Map<String, String> map = new HashMap<>();
        map.put("transaction_id", String.valueOf(transactionId));
        map.put("status", status);

        //String requestJson = String.format("{\"transaction_id\":\"%d\",\"status\":\"%s\"}", transactionId, status);
        System.out.println(map);

        this.restTemplate.postForEntity("http://localhost:8081/transactionpaypal/updateTransaction",map, Void.class);
        System.out.println("ho inviato il post con rest template");

        return null;
    }
}


