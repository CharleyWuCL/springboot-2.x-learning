package com.springboot.chapter8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.springboot.chapter8.pojo.User;
import com.springboot.chapter8.repository.UserRepository;
import com.springboot.chapter8.service.UserService;

/**** imports ****/
@Controller
@RequestMapping("/user")
public class UserController  {
	
	// 后面会给出其操作的方法
	@Autowired
	private UserService userService = null;

	// 跳转到测试页面
	@RequestMapping("/page")
	public String page() {
		return "user";
	}
	
	/**
	 * 保存（新增或者更新）用户
	 * @param user -- 用户
	 * @return 用户信息
	 */
	@RequestMapping("/save")
	@ResponseBody
	public User saveUser(@RequestBody User user) {
		userService.saveUser(user);
		return user;
	}
	
	/***
	 * 获取用户
	 * @param id -- 用户主键
	 * @return 用户信息
	 */
	@RequestMapping("/get")
	@ResponseBody
	public User getUser(Long id) {
		User user = userService.getUser(id);
		return user;
	}
	
	
	/**
	 * 查询用户
	 * @param userName --用户名称
	 * @param note -- 备注
	 * @param skip -- 跳过用户个数
	 * @param limit -- 限制返回用户个数
	 * @return
	 */
	@RequestMapping("/find")
	@ResponseBody
	public List<User> addUser(String userName, String note, Integer skip, Integer limit) {
		List<User> userList = userService.findUser(userName, note, skip, limit);
		return userList;
	}
	
	/**
	 * 更新用户部分属性
	 * @param id —— 用户编号
	 * @param userName —— 用户名称
	 * @param note —— 备注
	 * @return 更新结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public UpdateResult updateUser(Long id, String userName, String note) {
		return userService.updateUser(id, userName, note);
	}
	
	/**
	 * 删除用户
	 * @param id -- 用户主键
	 * @return 删除结果
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public DeleteResult deleteUser(Long id) {
		return userService.deleteUser(id);
	}
	
	
	// 注入接口
    @Autowired
    private UserRepository userRepository = null;
    
    // 执行查询
    @RequestMapping("/byName")
    @ResponseBody
    public List<User> findByUserName(String userName) {
        return userRepository.findByUserNameLike(userName);
    }
    
    // 执行查询
    @RequestMapping("/findOr")
    @ResponseBody
    public User findUserByIdOrUserName(Long id, String userName) {
    	return userRepository.findUserByIdOrUserName(id, userName);
    }
}