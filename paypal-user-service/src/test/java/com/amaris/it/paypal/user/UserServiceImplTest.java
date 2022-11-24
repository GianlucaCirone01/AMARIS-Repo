package com.amaris.it.paypal.user;

import com.amaris.it.paypal.user.model.User;
import com.amaris.it.paypal.user.repository.UserRepository;
import com.amaris.it.paypal.user.service.UserServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserServiceImpl userService;

  @Before
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

  @Test
  public void getByUsernameTest() {
    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);

    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);

    User findUser = userService.getByUsername(user1.getUsername());
    assertEquals(findUser, user1);

  }

  @Test
  public void increaseBalanceTest() {

    double balance = 25;
    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);

    user1.setBalance(user1.getBalance() + balance);
    when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);

    User findUser = userService.getByUsername(user1.getUsername());
    findUser.setBalance(findUser.getBalance() + balance);

    assertEquals(findUser, user1);
  }

}
