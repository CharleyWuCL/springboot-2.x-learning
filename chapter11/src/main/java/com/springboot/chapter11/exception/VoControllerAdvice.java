package com.springboot.chapter11.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//控制器通知
@ControllerAdvice(
		// 指定拦截包的控制器
		basePackages = { "com.springboot.chapter11.controller.*" },
		// 限定被标注为@Controller或者@RestController的类才被拦截
		annotations = { Controller.class, RestController.class })
public class VoControllerAdvice {
	// 异常处理，可以定义异常类型进行拦截处理
	@ExceptionHandler(value = NotFoundException.class)
	// 以JSON表达方式响应
	@ResponseBody
	// 定义为服务器错误状态码
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> exception(HttpServletRequest request, NotFoundException ex) {
		Map<String, Object> msgMap = new HashMap<>();
		// 获取异常信息
		msgMap.put("code", ex.getCode());
		msgMap.put("message", ex.getCustomMsg());
		return msgMap;
	}
}
