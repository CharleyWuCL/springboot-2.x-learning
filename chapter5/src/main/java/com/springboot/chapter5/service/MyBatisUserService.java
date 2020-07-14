package com.springboot.chapter5.service;
import com.springboot.chapter5.pojo.User;
public interface MyBatisUserService {
    public User getUser(Long id);
}