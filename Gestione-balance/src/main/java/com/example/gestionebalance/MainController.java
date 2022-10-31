package com.example.gestionebalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.paypal_model.Transaction;
import java.util.NoSuchElementException;


@Controller
@RequestMapping(path="/demo")
public class MainController {
    @Autowired
    private UserService userService;

    @PostMapping(path="/add")
    @ResponseBody
    public User addNewUser (@RequestBody User userDto) {

        return this.userService.addNew(userDto);

    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {

        return userService.getAll();
    }
    @GetMapping(path="/findID/{username}")
    @ResponseBody
    public ResponseEntity<Integer> getIDUsers(@PathVariable String username){

         return  userService.getbyUsername(username);

    }
    @PutMapping(path="/updateBalance/{username}/{balance}")
    @ResponseBody
    public User updateBalance (@PathVariable String username, @PathVariable Float balance){

        return this.userService.setNewBalance(username, balance);
    }

    @PostMapping(path="/transaction")
    @ResponseBody
    public  ResponseEntity<String> transaction (@RequestBody Transaction traDto) throws NoSuchFieldException {

        return this.userService.moveMoney(traDto);

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String generalError() {
        return "something went wrong, try again";
    }

   @ExceptionHandler(RuntimeException.class)
   @ResponseBody
    public String existError() {
        return "username già in uso";
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public String databaseError(){
        return "Utente non presente";
    }

    @ExceptionHandler(NoSuchFieldException.class)
    @ResponseBody
    public String balanceError(){
        return "Credito insufficiente";
    }

}