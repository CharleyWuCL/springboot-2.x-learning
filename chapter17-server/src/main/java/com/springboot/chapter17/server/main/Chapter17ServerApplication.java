package com.springboot.chapter17.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Chapter17ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chapter17ServerApplication.class, args);
	}
}
