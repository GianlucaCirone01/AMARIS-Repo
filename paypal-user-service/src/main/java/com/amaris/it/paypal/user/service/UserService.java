package com.amaris.it.paypal.user.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.user.entity.User;
import com.amaris.it.paypal.user.repository.TransactionStatusNotifier;
import com.amaris.it.paypal.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class UserService {

  @Autowired
  private TransactionStatusNotifier transactionStatusNotifier;
  @Autowired
  private UserRepository userRepository;

  /**
   * Questo metodo aggiunge e restituisce un
   * nuovo utente, se non è gia presente
   * nel database.
   * Setta il balance dell'utente a 0.
   */
  public User addNew(User dto) {

    final User u = userRepository.findIdByUsername(dto.getUsername());

    if (u != null) {
      throw new RuntimeException();
    }
    final User n = new User();
    n.setUsername(dto.getUsername());
    n.setName(dto.getName());
    n.setSurname(dto.getSurname());
    n.setBalance(0.0);

    return this.userRepository.save(n);
  }

  public Iterable<User> getAll() {

    return userRepository.findAll();
  }

  /**
   * Tramite l'username restituisce l'id dell'utente
   * se è presente nel database
   */
  public ResponseEntity<Long> getbyUsername(String username) {

    final User u = userRepository.findIdByUsername(username);

    if (u == null) {
      throw new NoSuchElementException();
    }

    return new ResponseEntity<>(u.getId(), HttpStatus.OK);
  }

  /**
   * Se presente tramite ricerca dell'username,
   * aggiunge al vecchio balance una certa somma e
   * l'aggiorna.
   * Restituisce l'user.
   */
  public User setNewBalance(String username, Double balance) {

    final User u = userRepository.findIdByUsername(username);
    if (u == null) {
      throw new NoSuchElementException();
    }

    u.setBalance(u.getBalance() + balance);
    this.userRepository.save(u);

    return u;
  }

  /**
   * Questo metodo permette il trasferimento dei soldi
   * da un utente all'altro. Controlla se è presente già l'id della transazione
   * e se è cosi oltre al comportamento base del metodo utilizza un metodo notify per
   * settare lo status della transazione in base all'esito.
   * Controlla e lancia eccezioni se non son presenti gli
   * utenti (mittente e destinatario) nel database o se il credito è inferiore
   * alla somma da dover trasferire.
   */
  @Transactional
  public ResponseEntity<String> moveMoney(TransactionRequest traDto) throws NoSuchFieldException {


    final Optional<User> user1 = userRepository.findById(traDto.getSenderUserId());
    final Optional<User> user2 = userRepository.findById(traDto.getReceiverUserId());

    if ((user1.isEmpty()) || (user2.isEmpty())) {
      if (traDto.getTransactionId() != null) {
        this.transactionStatusNotifier.notify(traDto.getTransactionId(),
            TransactionResult.TransactionStatus.ERROR);
        throw new NoSuchElementException();
      } else {
        throw new NoSuchElementException();
      }
    }

    final User userGet1 = user1.get();
    final User userGet2 = user2.get();

    if (userGet1.getBalance() < traDto.getAmount()) {
      if (traDto.getTransactionId() != null) {
        this.transactionStatusNotifier.notify(traDto.getTransactionId(),
            TransactionResult.TransactionStatus.ERROR);
      }
      throw new NoSuchFieldException();
    }

    userGet1.setBalance(userGet1.getBalance() - traDto.getAmount());
    this.userRepository.save(userGet1);

    userGet2.setBalance(userGet2.getBalance() + traDto.getAmount());
    this.userRepository.save(userGet2);

    return new ResponseEntity<>("Transazione da "
        + traDto.getAmount()
        + " Nuovo saldo Utente d'inizio: "
        + userGet1.getBalance().toString()
        + " Nuovo saldo Utente di fine: "
        + userGet2.getBalance().toString()
        , HttpStatus.OK);
  }

}




