package com.amaris.it.paypal.user.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.user.entity.User;
import com.amaris.it.paypal.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  /**
   * Questo metodo aggiunge e restituisce un
   * nuovo utente, se non è gia presente
   * nel database.
   * Setta il balance dell'utente a 0.
   */
  public User addNew(User dto) {

    final User u = userRepository.findByUsername(dto.getUsername());

    if (u != null) {
      throw new DuplicateKeyException(String.format("User with username %s already present",
          dto.getUsername()));
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
   * Tramite l'username restituisce l'utente
   * se è presente nel database
   */
  public User getByUsername(String username) {

    final User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new NoSuchElementException(String.format("User with username %s not found",
          username));
    }

    return user;
  }

  /**
   * Se presente tramite ricerca dell'username,
   * aggiunge al vecchio balance una certa somma e
   * l'aggiorna.
   * Restituisce l'user.
   */
  public User setNewBalance(String username, Double balance) {

    final User u = userRepository.findByUsername(username);
    if (u == null) {
      throw new NoSuchElementException(String.format("User with username %s not found",
          username));
    }

    u.setBalance(u.getBalance() + balance);

    return this.userRepository.save(u);
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
  public void moveMoney(TransactionRequest traDto) throws IllegalArgumentException {


    final User senderUser = userRepository.findById(traDto.getSenderUserId())
        .orElseThrow(() -> new NoSuchElementException(String.format("User with id %s not found",
            traDto.getSenderUserId())));
    final User receiverUser = userRepository.findById(traDto.getReceiverUserId())
        .orElseThrow(() -> new NoSuchElementException(String.format("User with id %s not found",
            traDto.getReceiverUserId())));

    if (senderUser.getBalance() < traDto.getAmount()) {
      throw new IllegalArgumentException(String.format("User with id %s not have enough money",
          senderUser.getId()));
    }

    senderUser.setBalance(senderUser.getBalance() - traDto.getAmount());
    this.userRepository.save(senderUser);

    receiverUser.setBalance(receiverUser.getBalance() + traDto.getAmount());
    this.userRepository.save(receiverUser);
  }

}




