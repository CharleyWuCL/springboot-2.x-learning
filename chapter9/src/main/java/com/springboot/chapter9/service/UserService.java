package com.springboot.chapter9.service;

import java.util.List;

import com.springboot.chapter9.pojo.User;

public interface UserService {
	
	User getUser(Long id);

	List<User> findUsers(String userName, String note);

}
