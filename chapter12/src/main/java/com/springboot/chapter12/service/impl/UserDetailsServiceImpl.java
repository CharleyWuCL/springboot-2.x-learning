package com.springboot.chapter12.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.chapter12.pojo.DatabaseRole;
import com.springboot.chapter12.pojo.DatabaseUser;
import com.springboot.chapter12.service.UserRoleService;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	// 注入服务接口
    @Autowired
    private UserRoleService userRoleService = null;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) 
            throws UsernameNotFoundException {
        // 获取数据库用户信息
        DatabaseUser dbUser = userRoleService.getUserByName(userName); 
        // 获取数据库角色信息
        List<DatabaseRole> roleList 
            = userRoleService.findRolesByUserName(userName);
        // 将信息转换为UserDetails对象
        return changeToUser(dbUser, roleList);
    }
    
    private UserDetails changeToUser(DatabaseUser dbUser,
            List<DatabaseRole> roleList) {
        // 权限列表
        List<GrantedAuthority> authorityList = new ArrayList<>();
        // 赋予查询到的角色
        for (DatabaseRole role : roleList) {
            GrantedAuthority authority 
                = new SimpleGrantedAuthority(role.getRoleName());
            authorityList.add(authority);
        }
        // 创建UserDetails对象，设置用户名、密码和权限
        UserDetails userDetails = new User(dbUser.getUserName(), 
            dbUser.getPwd(), authorityList);
        return userDetails;
    }

}
