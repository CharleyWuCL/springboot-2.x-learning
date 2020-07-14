package com.springboot.chapter8.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter8")
//指定扫描的包，用于扫描继承了MongoRepository的接口
@EnableMongoRepositories(basePackages="com.springboot.chapter8.repository", repositoryImplementationPostfix ="Stuff")
public class Chapter8Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter8Application.class, args);
	}
}
