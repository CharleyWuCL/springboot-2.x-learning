package com.springboot.chapter6.service;

import com.springboot.chapter6.pojo.User;

public interface UserService {
	// 获取用户信息
	public User getUser(Long id);

	// 新增用户
	public int insertUser(User user);
}
