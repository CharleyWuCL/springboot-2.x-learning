package com.springboot.chapter3.config;

import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springboot.other.pojo.Squirrel;
public class IoCTest {
	
	public static void main(String[] args) throws SQLException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		Squirrel squirrel = ctx.getBean(Squirrel.class);
		squirrel.use();
	}
}
