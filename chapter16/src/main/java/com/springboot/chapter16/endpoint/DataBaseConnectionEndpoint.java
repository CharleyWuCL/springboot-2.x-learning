package com.springboot.chapter16.endpoint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

// 让Spring扫描类
@Component
// 定义端点
@Endpoint(
		// 端点id
		id = "dbcheck",
		// 是否默认的情况下是否启用端点
		enableByDefault = true)
public class DataBaseConnectionEndpoint {
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	@Value("${spring.datasource.url}")
	private String url = null;
	
	@Value("${spring.datasource.username}")
	private String username = null;

	@Value("${spring.datasource.password}")
	private String password = null;

	// 一个端点只能存在一个@ReadOperation标注的方法
	// 它代表的是HTTP的GET请求
	@ReadOperation
	public Map<String, Object> test() {
		Connection conn = null;
		Map<String, Object> msgMap = new HashMap<>();
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(url, username, password);
			msgMap.put("success", true);
			msgMap.put("message", "测试数据库连接成功");
		} catch (Exception ex) {
			msgMap.put("success", false);
			msgMap.put("message", ex.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close(); // 关闭数据库连接
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return msgMap;
	}

}
