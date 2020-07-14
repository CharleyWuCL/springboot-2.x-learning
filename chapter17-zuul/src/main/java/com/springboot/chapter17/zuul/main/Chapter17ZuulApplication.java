package com.springboot.chapter17.zuul.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter17.zuul")
//启动Zuul代理功能
@EnableZuulProxy
public class Chapter17ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chapter17ZuulApplication.class, args);
	}
}
