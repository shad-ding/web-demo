package com.example.webdemo.dao.service;

import com.example.webdemo.dao.domain.User;

public interface IUserService {
    User findByUserId(Integer userId);
}
