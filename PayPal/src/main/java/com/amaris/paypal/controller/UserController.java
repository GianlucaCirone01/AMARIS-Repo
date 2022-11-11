package com.amaris.paypal.controller;

import com.amaris.paypal.dao.UserRepository;
import com.amaris.paypal.model.User;
import com.amaris.paypalmodel.model.TransferBalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping(path = "/user")
public class UserController {


  @Autowired
  private UserRepository userRepository;

  @PostMapping("/user")
  public User addUser(@RequestBody User user) {
    return userRepository.saveUser(user);
  }

  @PutMapping("/user")
  public User updateUser(@RequestBody User user) {
    return userRepository.updateUser(user);
  }

  @GetMapping("/user/{id}")
  public User getById(@PathVariable("id") int id) {
    return userRepository.getById(id);
  }

  @GetMapping("/users")
  public List<User> allUsers() {
    return userRepository.allUsers();
  }

  @PutMapping("/user/{username}")
  public User chargeMoney(@RequestBody User user) {
    return userRepository.chargeMoney(user);
  }

  @PostMapping("/user/transfermoney")
  public void transferMoney(@RequestBody TransferBalance transferBalance) {
    userRepository.transferMoney(transferBalance);
  }


  @GetMapping("user/getIdByUsername/{username}")
  public int getIdByUsername(@PathVariable("username") String username) {
    return userRepository.getIdByUsername(username);
  }


}
