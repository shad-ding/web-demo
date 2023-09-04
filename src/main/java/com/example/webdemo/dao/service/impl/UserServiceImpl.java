package com.example.webdemo.dao.service.impl;

import com.example.webdemo.dao.domain.User;
import com.example.webdemo.dao.repository.UserRepository;
import com.example.webdemo.dao.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserId(Integer userId) {
        return this.userRepository.findByUserId(userId);
    }
}
