package com.springboot.chapter16.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**** imports ****/
@RestController
public class CloseController {
	@GetMapping("/close")
	public ModelAndView close(ModelAndView mv) {
		// 定义视图名称为close，让其跳转到对应的JSP中去
		mv.setViewName("close");
		return mv;
	}
}