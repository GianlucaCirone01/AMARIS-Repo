package com.amaris.paypal.paypal.dao;

import com.amaris.paypal.paypal.model.TransferBalance;
import com.amaris.paypal.paypal.model.User;

import java.util.List;

public interface UserRepository {
    User saveUser(User user);
    User updateUser(User user);
    User getById(int id);
    //User getByUsername(User user);
    String deleteById(int id);
    List<User> allUsers();
    User chargeMoney(User user);
    void transferMoney(TransferBalance transferBalance);

}
