package com.springboot.chapter10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**** imports ****/
@Controller
@RequestMapping("/interceptor")
public class InterceptorController {
	@GetMapping("/start")
	public String start() {
		System.out.println("执行处理器逻辑");
		return "/welcome";
	}
}