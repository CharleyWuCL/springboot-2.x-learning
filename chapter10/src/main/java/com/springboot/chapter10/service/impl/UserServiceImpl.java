package com.springboot.chapter10.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.chapter10.dao.UserDao;
import com.springboot.chapter10.pojo.User;
import com.springboot.chapter10.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao = null;
	
	@Override
	public User getUser(Long id) {
		return userDao.getUser(id);
	}

	@Override
	public List<User> findUsers(String userName, String note) {
		return userDao.findUsers(userName, note);
	}

	@Override
	public int insertUser(User user) {
		return userDao.insertUser(user);
	}
	
}
