package com.amaris.paypal.model;

import com.amaris.paypalmodel.model.TransactionPojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTransactionStatusNotifier implements TransactionStatusNotifier {

  @Value("${updateStatus.url}")
  private String updateStatusUrl;
  private RestTemplate restTemplate = new RestTemplate();


  @Async
  @Override
  public void notify(int transactionId, String status) {

    TransactionPojo transactionPojo = new TransactionPojo(transactionId, status);
    try {
      restTemplate.postForEntity(updateStatusUrl, transactionPojo, Void.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    /*TODO dopo sostituire con un logger*/
    System.out.println("TransactionId: " + transactionId + " " + " Status: " + status);
  }

}
