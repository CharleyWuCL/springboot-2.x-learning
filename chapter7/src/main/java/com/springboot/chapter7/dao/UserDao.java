package com.springboot.chapter7.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.springboot.chapter7.pojo.User;

/**** imports ****/
@Repository
public interface UserDao {
    // 获取单个用户
    User getUser(Long id);
    
    // 保存用户
    int insertUser(User user);
    
    // 修改用户
    int updateUser(User user);
    
    // 查询用户，指定MyBatis的参数名称
    List<User> findUsers(@Param("userName") String userName,
            @Param("note") String note);    
    
    // 删除用户
    int deleteUser(Long id); 
}