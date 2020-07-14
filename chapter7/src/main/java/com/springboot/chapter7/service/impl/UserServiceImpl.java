package com.springboot.chapter7.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.chapter7.dao.UserDao;
import com.springboot.chapter7.pojo.User;
import com.springboot.chapter7.service.UserService;

/**** imports ****/
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao = null;

	// 插入用户，最后MyBatis会回填id，取结果id缓存用户
	@Override
	@Transactional
	@CachePut(value = "redisCache", key = "'redis_user_'+#result.id")
	public User insertUser(User user) {
		userDao.insertUser(user);
		return user;
	}

	// 获取id，取参数id缓存用户
	@Override
	@Transactional
	@Cacheable(value = "redisCache", key = "'redis_user_'+#id")
	public User getUser(Long id) {
		return userDao.getUser(id);
	}

	// 更新数据后，充值缓存，使用condition配置项使得结果返回为null，不缓存
	@Override
	@Transactional
	@CachePut(value = "redisCache", condition = "#result != 'null'", key = "'redis_user_'+#id")
	public User updateUserName(Long id, String userName) {
		// 此处调用getUser方法，该方法缓存注解失效，
		// 所以这里还会执行SQL，将查询到数据库最新数据
		User user = this.getUser(id);
		if (user == null) {
			return null;
		}
		user.setUserName(userName);
		userDao.updateUser(user);
		return user;

	}

	// 命中率低，所以不采用缓存机制
	@Override
	@Transactional
	public List<User> findUsers(String userName, String note) {
		return userDao.findUsers(userName, note);
	}

	// 移除缓存
	@Override
	@Transactional
	@CacheEvict(value = "redisCache", key = "'redis_user_'+#id", beforeInvocation = false)
	public int deleteUser(Long id) {
		return userDao.deleteUser(id);
	}
}