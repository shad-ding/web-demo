package com.example.webdemo.dao.repository;

import com.example.webdemo.dao.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserId(Integer userId);
}
