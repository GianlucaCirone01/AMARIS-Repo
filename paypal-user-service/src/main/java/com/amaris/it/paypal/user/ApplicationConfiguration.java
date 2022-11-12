package com.amaris.it.paypal.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
public class ApplicationConfiguration {

  @Bean
  protected RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
