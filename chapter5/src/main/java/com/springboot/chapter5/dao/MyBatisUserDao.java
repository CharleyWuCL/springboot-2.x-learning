package com.springboot.chapter5.dao;

import org.springframework.stereotype.Repository;

import com.springboot.chapter5.pojo.User;

/**** imports ****/
@Repository
public interface MyBatisUserDao {
	public User getUser(Long id);
}