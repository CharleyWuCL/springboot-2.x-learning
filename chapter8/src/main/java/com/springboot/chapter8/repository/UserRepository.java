package com.springboot.chapter8.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.springboot.chapter8.pojo.User;

// 标识为DAO层
@Repository
// 扩展MongoRepository接口
public interface UserRepository extends MongoRepository<User, Long> {	
	/**
	 * 符合JPA规范命名方法，则不需要再实现该方法也可用，
	 * 意在对满足条件的文档按照用户名称进行模糊查询
	 * @param userName -- 用户名称
	 * @return 满足条件的用户信息
	 */
	List<User> findByUserNameLike(String userName);
	
	
	/**
	 * 根据编号或者用户名查找用户
	 * @param id -- 编号
	 * @param userName -- 用户名
	 * @return 用户信息
	 */
	User findUserByIdOrUserName(Long id, String userName);
}