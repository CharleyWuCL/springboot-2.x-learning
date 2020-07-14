package com.springboot.chapter14.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import com.springboot.chapter14.enumeration.SexEnum;
import com.springboot.chapter14.pojo.User;

/**** imports ****/
// 定义扫描包
@SpringBootApplication(scanBasePackages="com.springboot.chapter14")
// 由于引入JPA，默认的情况下，需要配置数据源，
// 通过@EnableAutoConfiguration排除原有自动配置的数据源
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
// 在WebFlux下，驱动MongoDB的JPA接口
@EnableReactiveMongoRepositories(
    // 定义扫描的包
    basePackages="com.springboot.chapter14.repository")
public class Chapter14Application  {

    public static void main(String[] args) {
        SpringApplication.run(Chapter14Application.class, args);
    }
    
}