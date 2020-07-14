package com.springboot.chapter15.main;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**** imports ****/
// 定义扫描包
@SpringBootApplication(scanBasePackages = "com.springboot.chapter15")
// 定义扫描MyBatis接口
@MapperScan(annotationClass = Mapper.class, basePackages = "com.springboot.chapter15")
@EnableScheduling
public class Chapter15Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter15Application.class, args);
	}
}