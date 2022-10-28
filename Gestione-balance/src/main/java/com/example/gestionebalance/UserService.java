package com.example.gestionebalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.paypal_model.Transaction;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addNew(User dto) {

        User u = userRepository.trovaDaUsername(dto.getUsername());
        //User u = userRepository.findByUsername(dto.getUsername());

        if (u != null) {
            throw new RuntimeException();
        }
        User n = new User();
        n.setUsername(dto.getUsername());
        n.setName(dto.getName());
        n.setSurname(dto.getSurname());
        n.setBalance(0.0F);
        return this.userRepository.save(n);

    }

    public Iterable<User> getAll() {

        return userRepository.findAll();
    }

    public ResponseEntity<String> getbyUsername(User userDto){

        //User u = userRepository.findByUsername(username);
        User u = userRepository.trovaDaUsername(userDto.getUsername());

        if (u == null) {
            throw new NoSuchElementException();
        }

        return new ResponseEntity<>(u.getId().toString(), HttpStatus.OK);
        //return new ResponseEntity<>("Id non trovato", HttpStatus.BAD_REQUEST);
    }

    public User setNewBalance(String username, Float balance) {

       //User u = userRepository.findByUsername(username);
        User u = userRepository.trovaDaUsername(username);
        if (u == null) {
            throw new NoSuchElementException();
        }

        u.setBalance((u.getBalance() + balance));
        this.userRepository.save(u);

        return u;

    }

    @Transactional
    public ResponseEntity<String> moveMoney(Transaction traDto) throws NoSuchFieldException {
        //Iterable<User> utenti = userRepository.findAll();

        Optional<User> user1 = userRepository.findById(traDto.getIdStart());
        Optional<User> user2 = userRepository.findById(traDto.getIdEnd());

        //return new ResponseEntity<>("utente non presente",HttpStatus.FOUND);
        if ((user1.isEmpty())||(user2.isEmpty())) {
            throw new NoSuchElementException();
        }

        User utente1 = user1.get();
        User utente2 = user2.get();

        if (utente1.getBalance() < traDto.getMoney()){
               // return new ResponseEntity<>("Credito insufficiente",HttpStatus.FOUND);
            throw new NoSuchFieldException();
        }

        utente1.setBalance((utente1.getBalance() - traDto.getMoney()));
        this.userRepository.save(utente1);

        utente2.setBalance((utente2.getBalance() + traDto.getMoney()));
        this.userRepository.save(utente2);

        return new ResponseEntity<>("Transazione da " + traDto.getMoney() + " Nuovo saldo Utente d'inizio: " + utente1.getBalance().toString() + " Nuovo saldo Utente di fine: " + utente2.getBalance().toString(), HttpStatus.OK);

    }



}


