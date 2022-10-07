package com.amaris.paypal.paypal.dao;

import com.amaris.paypal.paypal.model.Utenti;
import org.apache.catalina.User;

import java.util.List;

public interface UserRepository {
    Utenti saveUser(Utenti user);
    Utenti updateUser(Utenti user);
    Utenti getById(int id);
    String deleteById(int id);
    List<Utenti> allUsers();

}
