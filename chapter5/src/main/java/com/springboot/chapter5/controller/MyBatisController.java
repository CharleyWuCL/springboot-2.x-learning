package com.springboot.chapter5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.chapter5.pojo.User;
import com.springboot.chapter5.service.MyBatisUserService;

/**** imports ****/
@Controller
@RequestMapping("/mybatis")
public class MyBatisController {
	
	@Autowired
	private MyBatisUserService myBatisUserService = null;
	
	@RequestMapping("/getUser")
	@ResponseBody
	public User getUser(Long id) {
		return myBatisUserService.getUser(id);
	}
}