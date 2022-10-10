package com.examplePayPal.dao.repository;

import com.examplePayPal.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {
    public User findIdByUsername(String username);
    public List<User> findByUsername(String username);
}
