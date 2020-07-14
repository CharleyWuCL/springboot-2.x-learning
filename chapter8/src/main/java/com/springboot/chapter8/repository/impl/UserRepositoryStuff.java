package com.springboot.chapter8.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.springboot.chapter8.pojo.User;

/****imports ****/
//定义为数据访问层
@Repository
//注意这里类名称，默认要求是接口名称（UserRepository） + "impl"
//这里Spring JPA会自动找到这个类作为接口方法实现
public class UserRepositoryStuff {
	
	@Autowired// 注入MongoTemplate
	private MongoTemplate mongoTmpl = null;

	// 注意方法名称和接口定义也需要保持一致
	public User findUserByIdOrUserName(Long id, String userName) {
		// 构造id查询准则
		Criteria criteriaId = Criteria.where("id").is(id);
		// 构造用户名查询准则
		Criteria criteriaUserName = Criteria.where("user_name").is(userName);
		Criteria criteria = new Criteria();
		// 使用$or操作符关联两个条件，形成或关系
		criteria.orOperator(criteriaId, criteriaUserName);
		Query query = Query.query(criteria);
		// 执行查询返回结果
		return mongoTmpl.findOne(query, User.class);
	}
}
