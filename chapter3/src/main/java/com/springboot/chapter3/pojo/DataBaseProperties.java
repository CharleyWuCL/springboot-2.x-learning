package com.springboot.chapter3.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties( "database")
public class DataBaseProperties {
	
	private String driverName = null;
	
	private String url = null;
	
	private String username = null;
	
	private String password = null;
	
	public void setDriverName(String driverName) {
		System.out.println(driverName);
		this.driverName = driverName;
	}
	
	public void setUrl(String url) {
		System.out.println(url);
		this.url = url;
	}
	
	
	public void setUsername(String username) {
		System.out.println(username);
		this.username = username;
	}
	
	public void setPassword(String password) {
		System.out.println(password);
		this.password = password;
	}
	
	public String getDriverName() {
		return driverName;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	
	
	
}
