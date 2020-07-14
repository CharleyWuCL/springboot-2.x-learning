package com.springboot.chapter13.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**** imports ***/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 如果你使用的不是Spring Boot依赖的服务器，才需要自己创建
	@Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
	
	// 注册服务器端点
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 增加一个聊天服务端点
        registry.addEndpoint("/socket").withSockJS();
        // 增加一个用户服务端点
        registry.addEndpoint("/wsuser").withSockJS();
    }

    // 定义服务器端点请求和订阅前缀
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 客户端订阅路径前缀
        registry.enableSimpleBroker("/sub", "/queue");
        // 服务端点请求前缀
        registry.setApplicationDestinationPrefixes("/request");
    }
}