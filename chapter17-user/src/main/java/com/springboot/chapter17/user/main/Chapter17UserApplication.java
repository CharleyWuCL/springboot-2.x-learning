package com.springboot.chapter17.user.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.springboot.chapter17.user")
public class Chapter17UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chapter17UserApplication.class, args);
	}
}
