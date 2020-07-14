package com.springboot.chapter6.dao;

import org.springframework.stereotype.Repository;

import com.springboot.chapter6.pojo.User;

@Repository
public interface UserDao {
    User getUser(Long id);
    int insertUser(User user);
}