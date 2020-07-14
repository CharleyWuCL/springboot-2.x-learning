package com.springboot.chapter12.dao;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.chapter12.pojo.DatabaseUser;

@Mapper
public interface UserDao {
	
	public DatabaseUser getUser(String userName);
}
