package com.amaris.it.paypal.user;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.user.model.User;
import com.amaris.it.paypal.user.repository.UserRepository;
import com.amaris.it.paypal.user.service.UserService;
import com.amaris.it.paypal.user.service.UserServiceImpl;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
<<<<<<< HEAD
import org.springframework.boot.test.context.SpringBootTest;
=======
import org.springframework.dao.DuplicateKeyException;
>>>>>>> 874cef68361bcb8bb74a6f0c44f1e870432fef68
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceImplTest {
  //@InjectMocks
  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserService userService = new UserServiceImpl();

  @Before("")
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void createUserTest() {

    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);

    when(userRepository.save(any(User.class))).thenReturn(user1);

    //User userSaved = userRepository.save(user1);
    User userSaved = userService.createUser(user1);
    assertEquals(user1.getUsername(), userSaved.getUsername());


  }

  @Test(expected = DuplicateKeyException.class)
  public void createUserTestAlreadyInUse() {

    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);

    when(userRepository.save(any(User.class))).thenReturn(user1);
    when(userRepository.findByUsername(user1.getUsername())).thenThrow(DuplicateKeyException.class);

    userService.createUser(user1);
  }

  @Test
  public void getAllTest() {
    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);
    User user2 = new User(2L, "Topak2", "Pieralli", "Marco", 0.0);

    List<User> list = new ArrayList<>();
    list.add(user1);
    list.add(user2);

    when(userRepository.findAll()).thenReturn(list);

    List<User> userList = (List<User>) userService.getAll();

    assertEquals(2, userList.size());

  }

<<<<<<< HEAD
=======
  @Test
  public void getByUsernameTest() {
    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);

    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);

    User findUser = userService.getByUsername(user1.getUsername());
    assertEquals(findUser, user1);

  }

  @Test(expected = NoSuchElementException.class)
  public void getByUsernameTestNotFound() {

    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);

    when(userRepository.findByUsername(user1.getUsername())).thenThrow(NoSuchElementException.class);
    User findUser = userService.getByUsername(user1.getUsername());
  }

  @Test
  public void increaseBalanceTest() {

    double balance = 25;
    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);

    when(userRepository.save(any(User.class))).thenReturn(user1);
    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);

    User findUser = userService.increaseBalance(user1.getUsername(), balance);

    assertEquals(findUser.getBalance(), user1.getBalance());

  }

  @Test
  public void transferMoneyTest() {

    TransactionRequest transactionRequest = new TransactionRequest(1L, 2L, 3L, 8.0);
    User user1 = new User(2L, "Topak1", "Pieralli", "Marco", 10.0);
    User user2 = new User(3L, "Topak2", "Pieralli", "Marco", 0.0);

    when(userRepository.findById(transactionRequest.getSenderUserId())).thenReturn(Optional.of(user1));
    when(userRepository.findById(transactionRequest.getReceiverUserId())).thenReturn(Optional.of(user2));

    userService.transferMoney(transactionRequest);
    assertEquals(transactionRequest.getAmount(), user2.getBalance());
  }

  @Test(expected = IllegalArgumentException.class)
  public void transferMoneyTestNotHaveEnoughMoney() {

    TransactionRequest transactionRequest = new TransactionRequest(1L, 2L, 3L, 8.0);
    User user1 = new User(2L, "Topak1", "Pieralli", "Marco", 2.0);
    User user2 = new User(3L, "Topak2", "Pieralli", "Marco", 0.0);

    when(userRepository.findById(transactionRequest.getSenderUserId())).thenReturn(Optional.of(user1));
    when(userRepository.findById(transactionRequest.getReceiverUserId())).thenReturn(Optional.of(user2));


    userService.transferMoney(transactionRequest);
  }

>>>>>>> 874cef68361bcb8bb74a6f0c44f1e870432fef68
}
