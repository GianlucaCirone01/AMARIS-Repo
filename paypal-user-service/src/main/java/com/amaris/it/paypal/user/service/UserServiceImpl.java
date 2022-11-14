package com.amaris.it.paypal.user.service;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.user.model.User;
import com.amaris.it.paypal.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User createUser(User dto) {

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

  @Override
  public Iterable<User> getAll() {

    return userRepository.findAll();
  }

  @Override
  public User getByUsername(String username) {

    final User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new NoSuchElementException(String.format("User with username %s not found",
          username));
    }

    return user;
  }

  @Override
  public User increaseBalance(String username, Double balance) {

    final User u = userRepository.findByUsername(username);
    if (u == null) {
      throw new NoSuchElementException(String.format("User with username %s not found",
          username));
    }

    u.setBalance(u.getBalance() + balance);

    return this.userRepository.save(u);
  }

  @Override
  @Transactional
  public void transferMoney(TransactionRequest traDto) {

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