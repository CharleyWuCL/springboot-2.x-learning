package com.springboot.chapter5.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.chapter5.enumeration.SexEnum;
import com.springboot.chapter5.pojo.User;
import com.springboot.chapter5.service.JdbcTmplUserService;

@Controller
@RequestMapping("/jdbc")
public class JdbcTemplateController {
	
	@Autowired
	private JdbcTmplUserService userService = null;
	
	@RequestMapping("/user/get")
	@ResponseBody
	public User getUser(Long id) {
		return userService.getUser3(id);
	}
	
	@RequestMapping("/user/find")
	@ResponseBody
	public List<User> findUsers(String userName, String note) {
		return userService.findUsers(userName, note);
	}

	@RequestMapping("/user/insert")
	@ResponseBody
	public Map<String, Object> insertUser(String userName, Integer sex, String note) {
		User user = new User();
		user.setNote(note);
		user.setUserName(userName);
		user.setSex(SexEnum.getEnumById(sex));
		int result = userService.insertUser(user);
		boolean success = result > 0;
		String msg = success ? "新增用户成功":"新增用户失败";
		return this.resultMap(success, msg);
	}

	@RequestMapping("/user/update")
	@ResponseBody
	public Map<String, Object> updateUser(Long id, String userName, Integer sex, String note) {
		User user = new User();
		user.setNote(note);
		user.setUserName(userName);
		user.setSex(SexEnum.getEnumById(sex));
		user.setId(id);
		int result = userService.updateUser(user);
		boolean success = result > 0;
		String msg = success ? "更新用户成功":"更新用户失败";
		return this.resultMap(success, msg);
	}

	@RequestMapping("/user/delete")
	@ResponseBody
	public Map<String, Object> deleteUser(Long id) {
		int result = userService.deleteUser(id);
		boolean success = result > 0;
		String msg = success ? "删除用户成功":"删除用户失败";
		return this.resultMap(success, msg);
	}
	
	private Map<String, Object> resultMap(Boolean success, String msg) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", success);
		map.put("message", msg);
		return map;
	}
}
