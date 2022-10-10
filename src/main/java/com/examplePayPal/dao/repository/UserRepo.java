package com.examplePayPal.dao.repository;

import com.examplePayPal.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    public User findIdByUsername(String username);
}
