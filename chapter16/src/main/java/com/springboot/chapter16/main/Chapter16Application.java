package com.springboot.chapter16.main;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.chapter16.endpoint.DataBaseConnectionEndpoint;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter16")
@MapperScan(basePackages="com.springboot.chapter16", annotationClass = Mapper.class)
public class Chapter16Application extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 密码编码器
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// 使用内存存储
		auth.inMemoryAuthentication()
				// 设置密码编码器
				.passwordEncoder(passwordEncoder)
				// 注册用户admin，密码为abc,并赋予USER和ADMIN的角色权限
				.withUser("admin")
				// 可通过passwordEncoder.encode("abc")得到加密后的密码
				.password("$2a$10$5OpFvQlTIbM9Bx2pfbKVzurdQXL9zndm1SrAjEkPyIuCcZ7CqR6je").roles("USER", "ADMIN")
				// 连接方法and
				.and()
				// 注册用户myuser，密码为123456,并赋予USER的角色权限
				.withUser("myuser")
				// 可通过passwordEncoder.encode("123456")得到加密后的密码
				.password("$2a$10$ezW1uns4ZV63FgCLiFHJqOI6oR6jaaPYn33jNrxnkHZ.ayAFmfzLS").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 需要Spring Security保护的端点
		String[] endPoints = { "auditevents", "beans", "conditions", "configprops", "env", "flyway",
				"httptrace", "loggers", "liquibase", "metrics", "mappings", "scheduledtasks", 
				"sessions", "shutdown", "threaddump" };
		// 定义需要验证的端点
		// http.requestMatcher(EndpointRequest.to(endPoints))
		http.authorizeRequests().antMatchers("/manage/**").hasRole("ADMIN")
				// 请求关闭页面需要ROLE_ADMIN橘色
				.antMatchers("/close").hasRole("ADMIN")
				.and().formLogin().and()
				// 启动HTTP基础验证
				.httpBasic();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Chapter16Application.class, args);
	}
}
