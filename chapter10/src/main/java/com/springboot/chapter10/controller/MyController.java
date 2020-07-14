package com.springboot.chapter10.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.chapter10.pojo.ValidatorPojo;

@RequestMapping("/my")
@Controller
public class MyController {
	/**
	 * 在无注解下获取参数，要求参数名称和HTTP请求参数名称一致.
	 * 
	 * @param intVal
	 *            -- 整数
	 * @param longVal
	 *            -- 长整形
	 * @param str
	 *            --字符串
	 * @return 响应JSON参数
	 */
	// HTTP GET请求
	@GetMapping("/no/annotation")
	@ResponseBody
	public Map<String, Object> noAnnotation(Integer intVal, Long longVal, String str) {
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("intVal", intVal);
		paramsMap.put("longVal", longVal);
		paramsMap.put("str", str);
		return paramsMap;
	}

	/**
	 * 通过注解@RequestParam获取参数
	 * 
	 * @param intVal
	 *            -- 整数
	 * @param longVal
	 *            -- 长整形
	 * @param str
	 *            --字符串
	 * @return 响应JSON数据集
	 */
	@GetMapping("/annotation")
	@ResponseBody
	public Map<String, Object> requestParam(@RequestParam("int_val") Integer intVal,
			@RequestParam("long_val") Long longVal, @RequestParam("str_val") String strVal) {
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("intVal", intVal);
		paramsMap.put("longVal", longVal);
		paramsMap.put("strVal", strVal);
		return paramsMap;
	}

	@GetMapping("/requestArray")
	@ResponseBody
	public Map<String, Object> requestArray(int[] intArr, Long[] longArr, String[] strArr) {
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("intArr", intArr);
		paramsMap.put("longArr", longArr);
		paramsMap.put("strArr", strArr);
		return paramsMap;
	}

	// 映射JSP页面
	@GetMapping("/format/form")
	public String showFormat() {
		return "/format/formatter";
	}

	// 获取提交参数
	@PostMapping("/format/commit")
	@ResponseBody
	public Map<String, Object> format(Date date,
			@NumberFormat(pattern = "#,###.##") Double number) {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("date", date);
		dataMap.put("number", number);
		return dataMap;
	}
	
	@GetMapping("/valid/page")
	public String validPage() {
	    return "/validator/pojo";
	}

	/***
	 * 解析验证参数错误
	 * @param vp —— 需要验证的POJO，使用注解@Valid 表示验证
	 * @param errors  错误信息，它由Spring MVC通过验证POJO后自动填充
	 * @return 错误信息Map
	 */
	@RequestMapping(value = "/valid/validate")
	@ResponseBody
	public Map<String, Object> validate(
	        @Valid @RequestBody ValidatorPojo vp, Errors errors) {
	    Map<String, Object> errMap = new HashMap<>();
	    // 获取错误列表
	    List<ObjectError> oes = errors.getAllErrors();
	    for (ObjectError oe : oes) {
	        String key = null;
	        String msg = null;
	        // 字段错误
	        if (oe instanceof FieldError) {
	            FieldError fe = (FieldError) oe;
	            key = fe.getField();// 获取错误验证字段名
	        } else {
	            // 非字段错误
	            key = oe.getObjectName();// 获取验证对象名称
	        }
	        // 错误信息
	        msg = oe.getDefaultMessage();
	        errMap.put(key, msg);
	    }
	    return errMap;
	}
}
