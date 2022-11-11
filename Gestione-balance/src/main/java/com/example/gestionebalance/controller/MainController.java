package com.example.gestionebalance.controller;

import com.example.gestionebalance.entity.User;
import com.example.gestionebalance.repository.TransactionStatusNotifier;
import com.example.gestionebalance.service.UserService;
import com.example.paypal_model.entity.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
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
public class MainController {

  @Autowired
  private UserService userService;
  @Autowired
  private TransactionStatusNotifier transactionStatusNotifier;

  private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

  @PostMapping(path = "/add")
  @ResponseBody
  public User addNewUser(@RequestBody User userDto) {

    final User u = this.userService.addNew(userDto);
    final Integer id = this.userService.getbyUsername(userDto.getUsername()).getBody();

    LOGGER.log(Level.parse("INFO"),
        String.format("A new User was added to the DB with User ID: %d", id));

    return u;
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<User> getAllUsers() {

    return userService.getAll();
  }

  @GetMapping(path = "/findID/{username}")
  @ResponseBody
  public ResponseEntity<Integer> getIDUsers(@PathVariable String username) {

    return userService.getbyUsername(username);
  }

  @PutMapping(path = "/updateBalance/{username}/{balance}")
  @ResponseBody
  public User updateBalance(@PathVariable String username, @PathVariable Float balance) {

    return this.userService.setNewBalance(username, balance);
  }

  @PostMapping(path = "/transaction")
  @ResponseBody
  public ResponseEntity<String> transaction(
      @RequestBody Transaction traDto) throws NoSuchFieldException {

    final ResponseEntity<String> result = this.userService.moveMoney(traDto);

    if ((result != null) && (traDto.getIdTransaction() != null)) {
      this.transactionStatusNotifier.notify(traDto.getIdTransaction(), "COMPLETE");
    }

    LOGGER.log(Level.WARNING, "Correctly executed transaction");
    return result;
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public String generalError() {

    final String ex = "Something went wrong, try again";
    LOGGER.log(Level.parse("SEVERE"),
        "Eccezione [" + Exception.class.getName() + "] catturata | " + ex
        , new Exception()
    );
    return ex;
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  public String existError() {

    final String ex = "Username already in use";
    LOGGER.log(Level.parse("SEVERE"),
        "Eccezione [" + RuntimeException.class.getName() + "] catturata | " + ex
        , new RuntimeException()
    );
    return ex;
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseBody
  public String databaseError() {

    final String ex = "User not present";
    LOGGER.log(Level.parse("SEVERE"),
        "Eccezione [" + NoSuchElementException.class.getName() + "] catturata | " + ex
        , new NoSuchElementException()
    );
    return ex;
  }

  @ExceptionHandler(NoSuchFieldException.class)
  @ResponseBody
  public String balanceError() {

    final String ex = "Insufficient credit";

    LOGGER.log(Level.SEVERE,
        "Eccezione [" + NoSuchFieldException.class.getName() + "] catturata | " + ex,
        new NoSuchFieldException()
    );
    return ex;
  }

}