package com.example.gestionebalance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.paypal_model.Transaction;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping(path="/demo")
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionStatusNotifier transactionStatusNotifier;


    @PostMapping(path="/add")
    @ResponseBody
    public User addNewUser (@RequestBody User userDto) {

        User u = this.userService.addNew(userDto);
        Integer id = this.userService.getbyUsername(userDto.getUsername()).getBody();
        log.info(String.format("un nuovo User è stato aggiunto al DB con User ID: %d",id));

        return u;
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
        
        ResponseEntity<String> result = this.userService.moveMoney(traDto);
        if ( (result != null) && (traDto.getIdTra()!= null) ) {
            this.transactionStatusNotifier.notify(traDto.getIdTra(), "COMPLETE");
        }
        log.info(String.format("Transazione eseguita correttamente"));
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String generalError() {

        String ex ="something went wrong, try again";
        log.error(String.format("Eccezone catturata | Something went wrong, try again"));
        return ex;
    }

   @ExceptionHandler(RuntimeException.class)
   @ResponseBody
    public String existError() {

       String ex ="username già in uso";
       log.error(String.format("Eccezone catturata | Username già in uso"));
       return ex;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public String databaseError(){

        String ex ="Utente non presente";
        log.error(String.format("Eccezone catturata |Utente non presente "));
        return ex;
    }

    @ExceptionHandler(NoSuchFieldException.class)
    @ResponseBody
    public String balanceError(){

        String ex ="Credito insufficiente";
        log.error(String.format("Eccezone catturata |Credito insufficiente"));
        return ex;
    }

}