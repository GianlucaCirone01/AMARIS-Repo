package com.amaris.paypal.paypal.controller;

import com.amaris.paypal.paypal.dao.UserRepository;
import com.amaris.paypal.paypal.model.TransferBalance;
import com.amaris.paypal.paypal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/user")
    public User addUser(@RequestBody User user){
        return userRepository.saveUser(user);
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user){
        return userRepository.updateUser(user);
    }

    @GetMapping("/user/{id}")
    public User getById(@PathVariable("id") int id){
        return userRepository.getById(id);
    }

    @GetMapping("/users")
    public List<User> allUsers(){
        return userRepository.allUsers();
    }

    @PutMapping("/user/{username}")
    public User chargeMoney(@RequestBody User user){return userRepository.chargeMoney(user);}

    @PutMapping("/user/transfermoney")
    public void transferMoney(@RequestBody TransferBalance transferBalance) {
        userRepository.transferMoney(transferBalance);

    }


    /*@GetMapping("/user/{username}")
    public User getByUsername(@RequestBody User user){
        return userRepository.getByUsername(user);
    }*/


}
