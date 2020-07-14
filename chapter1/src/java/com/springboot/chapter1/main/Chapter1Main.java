package com.springboot.chapter1.main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class Chapter1Main {
	
	public static void main(String[] args) {
		SpringApplication.run(Chapter1Main.class, args);
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public Map<String, String> test() {
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
		return map;
	}
}
