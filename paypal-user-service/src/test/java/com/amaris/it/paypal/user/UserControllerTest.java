package com.amaris.it.paypal.user;

import com.amaris.it.paypal.messages.model.TransactionRequest;
import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.user.controller.UserController;
import com.amaris.it.paypal.user.model.User;
import com.amaris.it.paypal.user.notifier.TransactionStatusNotifier;
import com.amaris.it.paypal.user.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  @InjectMocks
  UserController userController;

  @Mock
  UserServiceImpl userService;

  @Mock
  TransactionStatusNotifier transactionStatusNotifier;

  @Test
  public void addNewUserTest() {

    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);

    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    when(userService.createUser(any(User.class))).thenReturn(user1);

    User userCreate = userController.addNewUser(user1);

    assertEquals(userCreate, user1);

  }

  @Test
  public void getAllUsersTest() {

    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);
    User user2 = new User(2L, "Topak2", "Pieralli", "Marco", 0.0);

    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    List<User> list = new ArrayList<>();
    list.add(user1);
    list.add(user2);

    when(userService.getAll()).thenReturn(list);

    List<User> userList = (List<User>) userController.getAllUsers();

    assertEquals(2, userList.size());
    assertEquals(userList.get(1), list.get(1));
  }

  @Test
  public void getIdByUsernameTest() {

    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);

    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    when(userService.getByUsername(user1.getUsername())).thenReturn(user1);

    Long idUser = userController.getIdByUsername(user1.getUsername()).getBody();

    assertEquals(idUser, user1.getId());

  }

  @Test
  public void updateBalanceTest() {
    User user1 = new User(1L, "Topak1", "Pieralli", "Marco", 0.0);
    double balance = 25;

    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    when(userService.increaseBalance(user1.getUsername(), balance)).thenReturn(user1);

    User findUser = userController.updateBalance(user1.getUsername(), balance);

    assertEquals(findUser.getBalance(), user1.getBalance());

  }

  @Test
  public void transactionTest() throws JsonProcessingException {

    TransactionRequest transactionRequest = new TransactionRequest(1L, 2L, 3L, 8.0);
    User user1 = new User(2L, "Topak1", "Pieralli", "Marco", 10.0);
    User user2 = new User(3L, "Topak2", "Pieralli", "Marco", 0.0);

    String result = "COMPLETE";

    doNothing().when(userService).transferMoney(transactionRequest);
    ResponseEntity<Void> responseEntity = userController.transaction(transactionRequest);

    verify(transactionStatusNotifier)
        .notify(1L, TransactionResult.TransactionStatus.valueOf(result));
    assertEquals(responseEntity, ResponseEntity.ok().build());
  }

  @Test(expected = Exception.class)
  public void transactionTestError() throws JsonProcessingException {

    TransactionRequest transactionRequest = new TransactionRequest(1L, 2L, 3L, 8.0);
    User user1 = new User(2L, "Topak1", "Pieralli", "Marco", 10.0);
    User user2 = new User(3L, "Topak2", "Pieralli", "Marco", 0.0);

    String result = "Error";

    doThrow().when(userService).transferMoney(transactionRequest);
    verify(transactionStatusNotifier)
        .notify(1L, TransactionResult.TransactionStatus.valueOf(result));

    ResponseEntity<Void> responseEntity = userController.transaction(transactionRequest);
  }

  @Test
  public void transactionTestIdNull() throws JsonProcessingException {

    TransactionRequest transactionRequest = new TransactionRequest(null, 2L, 3L, 8.0);
    User user1 = new User(2L, "Topak1", "Pieralli", "Marco", 10.0);
    User user2 = new User(3L, "Topak2", "Pieralli", "Marco", 0.0);

    String result = "COMPLETE";

    doNothing().when(userService).transferMoney(transactionRequest);
    ResponseEntity<Void> responseEntity = userController.transaction(transactionRequest);

    verify(transactionStatusNotifier, never())
        .notify(null, TransactionResult.TransactionStatus.valueOf(result));
    assertEquals(responseEntity, ResponseEntity.ok().build());
  }

}
