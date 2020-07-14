package com.springboot.chapter10.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**** imports ****/
@Controller
@RequestMapping("/i18n")
public class I18nController {
	// 注入国际化消息接口对象
	@Autowired
	private MessageSource messageSource;

	// 后台获取国际化信息和打开国际化视图
	@GetMapping("/page")
	public String page(HttpServletRequest request) {
		// 后台获取国际化区域
		Locale locale = LocaleContextHolder.getLocale();
		// 获取国际化消息
		String msg = messageSource.getMessage("msg", null, locale);
		System.out.println("msg = " + msg);
		// 返回视图
		return "i18n/internationalization";
	}
}