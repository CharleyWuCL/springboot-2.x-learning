package com.springboot.chapter3.main;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {"com.springboot.chapter3"})
@PropertySource(value={"classpath:jdbc.properties"}, ignoreResourceNotFound=true)
@ImportResource(value = {"classpath:spring-other.xml"})
public class Chapter3Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter3Application.class, args);
	}
}
