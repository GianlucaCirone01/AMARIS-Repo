package com.amaris.paypal.paypal.controller;

import com.amaris.paypal.paypal.dao.UserRepository;
import com.amaris.paypal.paypal.model.Utenti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/user")
    public Utenti addUser(@RequestBody Utenti user){
        return userRepository.saveUser(user);
    }

    @PutMapping("/user")
    public Utenti updateUser(@RequestBody Utenti user){
        return userRepository.updateUser(user);
    }

    @GetMapping("/user/{id}")
    public Utenti getUser(@PathVariable("id") int id){
        return userRepository.getById(id);
    }

    @GetMapping("/users")
    public List<Utenti> getUser(){
        return userRepository.allUsers();
    }




}
