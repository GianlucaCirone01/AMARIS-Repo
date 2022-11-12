package com.amaris.it.paypal.user.repository;

import com.amaris.it.paypal.user.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {

  @Query(value = "SELECT u FROM User u WHERE u.username= :username")
  User findIdByUsername(@Param("username") String username);

}