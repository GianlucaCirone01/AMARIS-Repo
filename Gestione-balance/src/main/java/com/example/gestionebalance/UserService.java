package com.example.gestionebalance;

import com.example.paypal_model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;



@Service
public class UserService {


    @Autowired
    private TransactionStatusNotifier transactionStatusNotifier;
    @Autowired
    private UserRepository userRepository;

    /*
    * Questo metodo aggiunge e restituisce un
    * nuovo utente, se non è gia presente
    * nel database.
    * Setta il balance dell'utente a 0.
    */
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

        //log.info(String.format("un nuovo User è stato creato con Username: %s", dto.getUsername()));
        return this.userRepository.save(n);

    }

    public Iterable<User> getAll() {

        return userRepository.findAll();
    }

    /*
    * Tramite l'username restituisce l'id dell'utente
    * se è presente nel database
    */
    public ResponseEntity<Integer> getbyUsername(String username) {

        //User u = userRepository.findByUsername(username);
        User u = userRepository.trovaDaUsername(username);

        if (u == null) {
            throw new NoSuchElementException();
        }

        return new ResponseEntity<>(u.getId(), HttpStatus.OK);
        //return new ResponseEntity<>("Id non trovato", HttpStatus.BAD_REQUEST);
    }

    /*
    * Se presente tramite ricerca dell'username,
    * aggiunge al vecchio balance una certa somma e
    * l'aggiorna.
    * Restituisce l'user.
    */
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

    /*
     * Questo metodo permette il trasferimento dei soldi
     * da un utente all'altro. Controlla se è presente già l'id della transazione
     * e se è cosi oltre al comportamento base del metodo utilizza un metodo notify per
     * settare lo status della transazione in base all'esito.
     * Controlla e lancia eccezioni se non son presenti gli
     * utenti (mittente e destinatario) nel database o se il credito è inferiore
     * alla somma da dover trasferire.
     */
    @Transactional
    public ResponseEntity<String> moveMoney(Transaction traDto) throws NoSuchFieldException {
        //Iterable<User> utenti = userRepository.findAll();

        Optional<User> user1 = userRepository.findById(traDto.getIdStart());
        Optional<User> user2 = userRepository.findById(traDto.getIdEnd());

        //return new ResponseEntity<>("utente non presente",HttpStatus.FOUND);
        if ((user1.isEmpty())||(user2.isEmpty())) {
            if (traDto.getIdTra() != null) {
                this.transactionStatusNotifier.notify(traDto.getIdTra(), "ERROR");
                throw new NoSuchElementException();
            } else {
                throw new NoSuchElementException();
            }
        }

        User utente1 = user1.get();
        User utente2 = user2.get();

        if (utente1.getBalance() < traDto.getMoney()){
            if (traDto.getIdTra() != null) {
                this.transactionStatusNotifier.notify(traDto.getIdTra(), "ERROR");
                throw new NoSuchFieldException();
            } else {
                throw new NoSuchFieldException();
            }
        }

        utente1.setBalance((utente1.getBalance() - traDto.getMoney()));
        this.userRepository.save(utente1);

        utente2.setBalance((utente2.getBalance() + traDto.getMoney()));
        this.userRepository.save(utente2);

        //this.transactionStatusNotifier.notify(traDto.getIdTra(), "COMPLETE");
        return new ResponseEntity<>("Transazione da " + traDto.getMoney() + " Nuovo saldo Utente d'inizio: " + utente1.getBalance().toString() + " Nuovo saldo Utente di fine: " + utente2.getBalance().toString(), HttpStatus.OK);

    }

}




