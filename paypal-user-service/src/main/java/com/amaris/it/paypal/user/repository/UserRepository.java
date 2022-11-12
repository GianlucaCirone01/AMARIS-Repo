package com.amaris.it.paypal.user.repository;

import com.amaris.it.paypal.user.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

  @Query(value = "SELECT u FROM User u WHERE u.username= :username")
  User findByUsername(@Param("username") String username);

}