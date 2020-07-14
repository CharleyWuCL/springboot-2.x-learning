package com.springboot.chapter16.health;

import java.net.InetAddress;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

// 监测服务器是能能够访问万维网
@Component
public class WwwHealthIndicator extends AbstractHealthIndicator {
	// 通过监测百度服务器，看能否访问互联网
	private final static String BAIDU_HOST = "www.baidu.com";
	// 超时时间
	private final static int TIME_OUT = 3000;

	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		boolean status = ping();
		if (status) {
			// 健康指标为可用状态，并添加一个消息项
			builder.withDetail("message", "当前服务器可以访问万维网。").up();
		} else {
			// 健康指标为不再提供服务，并添加一个消息项
			builder.withDetail("message", "当前无法访问万维网").outOfService();
		}
	}

	// 监测百度服务器能够访问，用以判断能否访问万维网
	private boolean ping() throws Exception {
		try {
			// 当返回值是true时，说明host是可用的，false则不可。
			return InetAddress.getByName(BAIDU_HOST).isReachable(TIME_OUT);
		} catch (Exception ex) {
			return false;
		}
	}

}
