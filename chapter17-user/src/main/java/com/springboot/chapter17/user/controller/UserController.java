package com.springboot.chapter17.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.chapter17.user.pojo.UserPo;

/**** imports ****/
@RestController
public class UserController {
	// 日志
	private Logger log = Logger.getLogger(this.getClass());

	// 服务发现客户端
	@Autowired
	private DiscoveryClient discoveryClient = null;

	// 获取用户信息
	@GetMapping("/user/{id}")
	public UserPo getUserPo(@PathVariable("id") Long id) {
		ServiceInstance service = discoveryClient.getInstances("USER").get(0);
		log.info("【" + service.getServiceId() + "】:" + service.getHost() + ":" + service.getPort());
		UserPo user = new UserPo();
		user.setId(id);
		int level = (int) (id % 3 + 1);
		user.setLevel(level);
		user.setUserName("user_name_" + id);
		user.setNote("note_" + id);
		return user;
	}
	
	// 新增用户，POST请求，且以请求体(body)形式传递
	@PostMapping("/insert")
	public Map<String, Object> insertUser(@RequestBody UserPo user) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("success", true);
	    map.put("message", "插入用户信息【" +user.getUserName() + "】成功");
	    return map;
	}

	// 修改用户名，POST请求，其中用户编号使用请求头的形式传递
	@PostMapping("/update/{userName}") 
	public Map<String, Object> updateUsername(
	        @PathVariable("userName") String userName,
	        @RequestHeader("id") Long id) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("success", true);
	    map.put("message", "更新用户【" +id +"】名称【" +userName + "】成功");
	    return map;
	}
	
	@GetMapping("/timeout")
	public String timeout() {
	    // 生成一个3000之内的随机数
	    long ms = (long)(3000L*Math.random());
	    try {
	        // 程序延迟，有一定的概率超过2000毫秒
	        Thread.sleep(ms);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    return "熔断测试";
	}
}