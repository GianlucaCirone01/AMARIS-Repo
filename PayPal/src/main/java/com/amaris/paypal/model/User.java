package com.amaris.paypal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private int id;
  private String username;
  private String name;
  private String surname;
  private int balance;

  public User(int id) {
    this.id = id;
  }

  public User(int id, String username) {
    this.id = id;
    this.username = username;
  }
}
