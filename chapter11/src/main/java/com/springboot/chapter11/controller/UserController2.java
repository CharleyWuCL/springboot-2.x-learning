package com.springboot.chapter11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.chapter11.enumeration.SexEnum;
import com.springboot.chapter11.pojo.User;
import com.springboot.chapter11.service.UserService;
import com.springboot.chapter11.vo.UserVo;

/**** imports ****/
@RestController
public class UserController2 {

	// 用户服务接口
	@Autowired
	private UserService userService = null;

	// 映射JSP视图
	@GetMapping(value = "/restful2")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("restful");
		return mv;
	}

	// 获取用户
	@GetMapping(value = "/user2/{id}")
	public UserVo getUser(@PathVariable("id") Long id) {
		User user = userService.getUser(id);
		return changeToVo(user);
	}

	@GetMapping(value = "/user2/name/{id}",
			// 接受任意类型的请求体
			consumes = MediaType.ALL_VALUE,
			// 限定返回的媒体类型为文本
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String getUserName(@PathVariable("id") Long id) {
		User user = userService.getUser(id);
		// 返回字符
		return user.getUserName();
	}
	
	
	@PostMapping(value = "/user2/entity")
    public ResponseEntity<UserVo> insertUserEntity(
            @RequestBody UserVo userVo) {
        User user = this.changeToPo(userVo);
        userService.insertUser(user);
        UserVo result = this.changeToVo(user);
        HttpHeaders headers = new HttpHeaders();
        String success = 
            (result == null || result.getId() == null) ? "false" : "true";
        // 设置响应头，比较常用的方式
        headers.add("success", success);
        // 下面是使用集合（List）方式，不是太常用
        // headers.put("success", Arrays.asList(success));
        // 返回创建成功的状态码
        return new ResponseEntity<UserVo>(result, headers, HttpStatus.CREATED);
}

    @PostMapping(value = "/user2/annotation")
    // 指定状态码为201（资源已经创建）
    @ResponseStatus(HttpStatus.CREATED)
    public UserVo insertUserAnnotation(@RequestBody UserVo userVo) {
        User user = this.changeToPo(userVo);
        userService.insertUser(user);
        UserVo result = this.changeToVo(user);
        return result;
    }

	// 转换PO变为VO
	private UserVo changeToVo(User user) {
		UserVo userVo = new UserVo();
		userVo.setId(user.getId());
		userVo.setUserName(user.getUserName());
		userVo.setSexCode(user.getSex().getCode());
		userVo.setSexName(user.getSex().getName());
		userVo.setNote(user.getNote());
		return userVo;
	}

	// 转换Vo变为PO
	private User changeToPo(UserVo userVo) {
		User user = new User();
		user.setId(userVo.getId());
		user.setUserName(userVo.getUserName());
		user.setSex(SexEnum.getSexEnum(userVo.getSexCode()));
		user.setNote(userVo.getNote());
		return user;
	}
}