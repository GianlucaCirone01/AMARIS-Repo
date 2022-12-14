package com.amaris.it.paypal.transaction.connector;

import com.amaris.it.paypal.messages.model.TransactionRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestUserServiceConnector implements UserServiceConnector {

  @Value("${user_service.url}")
  private String userServiceUrl;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public Long getIdByUsername(String username) {
    final ResponseEntity<Long> response = this.restTemplate.getForEntity(userServiceUrl
        + "findID/" + username, Long.class);
    return response.getBody();
  }

  @Async
  @Override
  public void requestTransaction(TransactionRequest transactionRequest) {
    this.restTemplate.postForEntity(userServiceUrl
        + "transaction", transactionRequest, Void.class);
  }

 
}
