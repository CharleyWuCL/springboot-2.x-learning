package com.springboot.chapter14.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.chapter14.pojo.User;
import com.springboot.chapter14.service.UserService;
import com.springboot.chapter14.validator.UserValidator;
import com.springboot.chapter14.vo.UserVo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**** imports ****/
// REST风格控制器,
@RestController 
public class UserController {

	@Autowired
	private UserService userService;

	// 获取用户
	@GetMapping("/user/{id}")
	public Mono<UserVo> getUser(@PathVariable Long id) {
		return userService.getUser(id)
				// 从User对象转换为UserVo对象
				.map(u -> translate(u));
	}

	// 新增用户
	@PostMapping("/user")
	public Mono<UserVo> insertUser(@RequestBody User user) {
		return userService.insertUser(user)
				// 从User对象转换为UserVo对象
				.map(u -> translate(u));
	}

	// 更新用户
	@PutMapping("/user")
	public Mono<UserVo> updateUser(@RequestBody User user) {
		return userService.updateUser(user)
				// 从User对象转换为UserVo对象
				.map(u -> translate(u));
	}

	// 删除用户
	@DeleteMapping("/user/{id}")
	public Mono<Void> deleteUser(@PathVariable Long id) {
		return userService.deleteUser(id);
	}

	// 查询用户
	@GetMapping("/user/{userName}/{note}")
	public Flux<UserVo> findUsers(@PathVariable String userName, @PathVariable String note) {
		return userService.findUsers(userName, note)
				// 从User对象转换为UserVo对象
				.map(u -> translate(u));
	}

	@PostMapping("/user2/{user}")
	public Mono<UserVo> insertUser2(@PathVariable("user") User user) {
		return userService.insertUser(user)
				// 进行PO和VO之间的转换
				.map(u -> translate(u));
	}

	// 加入局部验证器
	@InitBinder
	public void initBinder(DataBinder binder) {
		binder.setValidator(new UserValidator());
	}

	@PostMapping("/user3")
	public Mono<UserVo> insertUser3(@Valid @RequestBody User user) {
		return userService.insertUser(user)
				// 进行PO和VO之间的转换
				.map(u -> translate(u));
	}
	
	@PutMapping("/user/name")
	public Mono<UserVo> updateUserName(@RequestHeader("id") Long id,  
	        @RequestHeader("userName") String userName) {
	    Mono<User> userMono = userService.getUser(id);
	    User user = userMono.block();
	    if (user == null) { // 查找不到用户信息，抛出运行异常消息......
	        throw new RuntimeException("找不到用户信息");
	    }
	    user.setUserName(userName);
	    return this.updateUser(user);
	}

	/***
	 * 完成PO到VO的转换
	 * 
	 * @param user
	 *            ——PO 持久对象
	 * @return UserVo ——VO 视图对象
	 */
	private UserVo translate(User user) {
		UserVo userVo = new UserVo();
		userVo.setUserName(user.getUserName());
		userVo.setSexCode(user.getSex().getCode());
		userVo.setSexName(user.getSex().getName());
		userVo.setNote(user.getNote());
		userVo.setId(user.getId());
		return userVo;
	}

}