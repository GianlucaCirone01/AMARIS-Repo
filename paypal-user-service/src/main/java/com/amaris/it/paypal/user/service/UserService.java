package com.amaris.it.paypal.user.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.user.model.User;

import javax.transaction.Transactional;

public interface UserService {
  /**
   * Questo metodo aggiunge e restituisce un
   * nuovo utente, se non è gia presente
   * nel database.
   * Setta il balance dell'utente a 0.
   */
  User createUser(User dto);

  Iterable<User> getAll();

  /**
   * Tramite l'username restituisce l'utente
   * se è presente nel database
   */
  User getByUsername(String username);

  /**
   * Se presente tramite ricerca dell'username,
   * aggiunge al vecchio balance una certa somma e
   * l'aggiorna.
   * Restituisce l'user.
   */
  User increaseBalance(String username, Double balance);

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
  void transferMoney(TransactionRequest traDto);
}
