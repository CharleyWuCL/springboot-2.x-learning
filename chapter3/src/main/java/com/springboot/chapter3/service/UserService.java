package com.springboot.chapter3.service;

import org.springframework.stereotype.Service;
import com.springboot.chapter3.pojo.User;

@Service
public class UserService {
	
	public void printUser(User user) {
		System.out.println("编号:" + user.getId());
		System.out.println("用户名称:" + user.getUserName());
		System.out.println("备注:" + user.getNote());
	}
}
