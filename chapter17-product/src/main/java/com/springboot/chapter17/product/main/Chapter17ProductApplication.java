package com.springboot.chapter17.product.main;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**** imports ****/
//@SpringBootApplication(scanBasePackages="com.springboot.chapter17.product")
@EnableFeignClients(basePackages = "com.springboot.chapter17.product")
// 启动断路器
//@EnableCircuitBreaker
//自定义扫描包
@ComponentScan(basePackages = "com.springboot.chapter17.product")
//开启Spring Boot应用、服务发现和断路器功能
@SpringCloudApplication
public class Chapter17ProductApplication {

    // 初始化RestTemplate
    @LoadBalanced // 多节点负载均衡
    @Bean(name = "restTemplate")
    public RestTemplate initRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter17ProductApplication.class, args);
    }
}