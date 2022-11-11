package com.amaris.paypal.dao;


import com.amaris.paypal.model.User;
import com.amaris.paypalmodel.model.TransferBalance;

import java.util.List;

public interface UserRepository {

  User saveUser(User user);

  User updateUser(User user);

  User getById(int id);

  String deleteById(int id);

  List<User> allUsers();

  User chargeMoney(User user);

  void transferMoney(TransferBalance transferBalance);

  int getIdByUsername(String username);

  String getTransactionStatus(int idTransaction);


}
