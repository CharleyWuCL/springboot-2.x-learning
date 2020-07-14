package com.springboot.chapter5.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.chapter5.pojo.User;
import com.springboot.chapter5.service.JdbcTmplUserService;

@Controller
@RequestMapping("/jdbcTmpl")
public class JdbcTmplController {

	@Autowired
	JdbcTmplUserService jdbcTmplUserService = null;
	
	@RequestMapping("/getUser")
	@ResponseBody
	public User getUser(Long id) {
		User user = jdbcTmplUserService.getUser2(id);
		return user;
	}
	
	@RequestMapping("/findUsers")
	@ResponseBody
	public List<User> findUsers(String userName, String note) {
		List<User> userList = jdbcTmplUserService.findUsers(userName, note);
		return userList;
	}
	
	@RequestMapping("/insertUser")
	@ResponseBody
	public Map<String, Object> insertUser(String userName, String note) {
		User user = new User();
		user.setUserName(userName);
		user.setNote(note);
		int result = jdbcTmplUserService.insertUser(user);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", result == 1);
		return resultMap;
	}
	
	
	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String, Object> updateUser(Long id, String userName, String note) {
		User user = new User();
		user.setUserName(userName);
		user.setNote(note);
		user.setId(id);
		int result = jdbcTmplUserService.updateUser(user);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", result == 1);
		return resultMap;
	}
	
	@RequestMapping("/deleteUser")
	@ResponseBody
	public Map<String, Object> deleteUser(Long id) {
		int result = jdbcTmplUserService.deleteUser(id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", result == 1);
		return resultMap;
	}
}
