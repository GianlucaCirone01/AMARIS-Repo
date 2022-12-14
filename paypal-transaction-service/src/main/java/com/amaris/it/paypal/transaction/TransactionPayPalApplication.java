package com.amaris.it.paypal.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class TransactionPayPalApplication {

  public static void main(String[] args) {
    SpringApplication.run(TransactionPayPalApplication.class, args);
  }

  @Bean
  protected RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
