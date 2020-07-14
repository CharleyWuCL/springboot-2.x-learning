package com.springboot.chapter4.aspect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.springboot.chapter4.aspect.MyAspect;
import com.springboot.chapter4.aspect.service.impl.UserServiceImpl;

@Configuration  
//启动@AspectJ切面编程  
@EnableAspectJAutoProxy  
//定义扫描包  
@ComponentScan(basePackages = {"com.springboot.chapter4.aspect.*"}, basePackageClasses = UserServiceImpl.class)
public class AspectConfig {
	
	@Bean(name = "myAspect")
	public MyAspect initMyAspect() {
	    return new MyAspect();
	}
}
