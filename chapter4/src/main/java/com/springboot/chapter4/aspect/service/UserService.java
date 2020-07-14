package com.springboot.chapter4.aspect.service;

import com.springboot.chapter4.pojo.User;

public interface UserService {
	
	public void printUser();

	public void printUser(User user);
	
	
	public void manyAspects();
}
