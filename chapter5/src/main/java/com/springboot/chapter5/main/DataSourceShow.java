package com.springboot.chapter5.main;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataSourceShow implements ApplicationContextAware {
	
	ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		DataSource dataSource = applicationContext.getBean(DataSource.class);
		System.out.println("--------------------------------");
		System.out.println(dataSource.getClass().getName());
		System.out.println("--------------------------------");
		JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
		System.out.println(jdbcTemplate.getClass().getName());
	}

}
