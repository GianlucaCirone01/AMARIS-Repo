package com.amaris.it.paypal.user.controller;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.user.model.User;
import com.amaris.it.paypal.user.notifier.TransactionStatusNotifier;
import com.amaris.it.paypal.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/user")
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private TransactionStatusNotifier transactionStatusNotifier;

  private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

  @PostMapping(path = "/add")
  @ResponseBody
  public User addNewUser(@RequestBody User userDto) {

    final User newUser = this.userService.createUser(userDto);

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

    final User user = this.userService.increaseBalance(username, balance);
    LOGGER.log(Level.INFO,
        String.format("Updated Balance to with User ID: %d, added %d", user.getId(), balance));
    return user;
  }

  @PostMapping(path = "/transaction")
  @ResponseBody
  public ResponseEntity<String> transaction(
      @RequestBody TransactionRequest transactionRequest) {
    LOGGER.log(Level.INFO, String.format("Requested transaction: %s", transactionRequest));

    try {
      this.userService.transferMoney(transactionRequest);
    } catch (Exception e) {
      notifyTransactionOutcome(transactionRequest.getTransactionId(),
          TransactionResult.TransactionStatus.ERROR);
      throw e;
    }
    notifyTransactionOutcome(transactionRequest.getTransactionId(),
        TransactionResult.TransactionStatus.COMPLETE);

    LOGGER.log(Level.INFO, "Correctly executed transaction");
    return ResponseEntity.ok().build();
  }

  private void notifyTransactionOutcome(Long transactionId,
      TransactionResult.TransactionStatus status) {
    if (transactionId != null) {
      this.transactionStatusNotifier.notify(transactionId,
          status);
    }
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleException(Exception e) {

    final String ex = "Something went wrong, try again";
    LOGGER.log(Level.SEVERE, ex, e);
    return e.getMessage();
  }

  @ExceptionHandler(DuplicateKeyException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public String handleDuplicateKeyException(DuplicateKeyException e) {

    final String ex = "Username already in use";
    LOGGER.log(Level.SEVERE, ex, e);
    return e.getMessage();
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public String handleNoSuchElementException(NoSuchElementException e) {

    final String ex = "User not present";
    LOGGER.log(Level.SEVERE, ex, e);
    return e.getMessage();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public String handleIllegalArgumentException(IllegalArgumentException e) {

    final String ex = "Insufficient credit";
    LOGGER.log(Level.SEVERE, ex, e);
    return e.getMessage();
  }

}