package com.amaris.it.paypal.user;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConditionalOnProperty(value = "deploy.notifier", havingValue = "kafka")
public class KafkaTopicConfig {
  @Bean
  public NewTopic topic1() {
    return TopicBuilder.name("Transaction").build();
  }
}
