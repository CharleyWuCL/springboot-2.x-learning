package com.springboot.chapter10.service;

import java.util.List;

import com.springboot.chapter10.pojo.User;

public interface UserService {
	
	User getUser(Long id);

	List<User> findUsers(String userName, String note);
	
	int insertUser(User user);

}
