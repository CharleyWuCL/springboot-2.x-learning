package com.springboot.chapter3.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.springboot.chapter3.*", 
excludeFilters = {@Filter(classes = {})})
public class AppConfig {
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
	    Properties props = new Properties();
	    props.setProperty("driver", "com.mysql.jdbc.Driver");
	    props.setProperty("url", "jdbc:mysql://localhost:3306/chapter3");
	    props.setProperty("username", "root");
	    props.setProperty("password", "123456");
	    DataSource dataSource = null;
	    try {
	        dataSource = BasicDataSourceFactory.createDataSource(props);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dataSource;
	}
}