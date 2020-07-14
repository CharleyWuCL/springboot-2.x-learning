package com.springboot.chapter17.dashboard.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/****imports****/
@SpringBootApplication
// 启用Hystrix仪表盘
@EnableHystrixDashboard
public class Chapter17DashboardApplication {
	public static void main(String[] args) {
		SpringApplication.run(Chapter17DashboardApplication.class, args);
	}
}