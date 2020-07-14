package com.springboot.chapter12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springboot.chapter12.pojo.DatabaseRole;

@Mapper
public interface RoleDao {
	
	public List<DatabaseRole> findRolesByUserName(String userName);
}
