package com.amaris.it.paypal.user.controller;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.user.entity.User;
import com.amaris.it.paypal.user.repository.TransactionStatusNotifier;
import com.amaris.it.paypal.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/demo")
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private TransactionStatusNotifier transactionStatusNotifier;

  private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

  @PostMapping(path = "/add")
  @ResponseBody
  public User addNewUser(@RequestBody User userDto) {

    final User newUser = this.userService.addNew(userDto);

    LOGGER.log(Level.INFO,
        String.format("A new User was added to the DB with User ID: %d", newUser.getId()));

    return newUser;
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<User> getAllUsers() {

    return userService.getAll();
  }

  @GetMapping(path = "/findID/{username}")
  @ResponseBody
  public ResponseEntity<Long> getIdByUsername(@PathVariable String username) {

    final User user = userService.getByUsername(username);
    return new ResponseEntity<>(user.getId(), HttpStatus.OK);
  }

  @PutMapping(path = "/updateBalance/{username}/{balance}")
  @ResponseBody
  public User updateBalance(@PathVariable String username, @PathVariable Double balance) {

    final User user = this.userService.setNewBalance(username, balance);
    LOGGER.log(Level.INFO,
        String.format("Updated Balance to with User ID: %d, added %d", user.getId(), balance));
    return user;
  }

  @PostMapping(path = "/transaction")
  @ResponseBody
  public ResponseEntity<String> transaction(
      @RequestBody TransactionRequest transactionRequest) throws NoSuchFieldException {
    LOGGER.log(Level.INFO, String.format("Requested transaction: %s", transactionRequest));

    this.userService.moveMoney(transactionRequest);

    if (transactionRequest.getTransactionId() != null) {
      this.transactionStatusNotifier.notify(transactionRequest.getTransactionId(),
          TransactionResult.TransactionStatus.COMPLETE);
    }

    LOGGER.log(Level.INFO, "Correctly executed transaction");
    return ResponseEntity.ok().build();
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public String generalError(Exception e) {

    final String ex = "Something went wrong, try again";
    LOGGER.log(Level.SEVERE, ex, e);
    return ex;
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  public String existError(RuntimeException e) {

    final String ex = "Username already in use";
    LOGGER.log(Level.SEVERE, ex, e);
    return ex;
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseBody
  public String databaseError(NoSuchElementException e) {

    final String ex = "User not present";
    LOGGER.log(Level.SEVERE, ex, e);
    return ex;
  }

  @ExceptionHandler(NoSuchFieldException.class)
  @ResponseBody
  public String balanceError(NoSuchFieldException e) {

    final String ex = "Insufficient credit";
    LOGGER.log(Level.SEVERE, ex, e);
    return ex;
  }

}