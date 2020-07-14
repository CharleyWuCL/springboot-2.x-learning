package com.springboot.chapter13.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.chapter13.pojo.User;
import com.springboot.chapter13.service.RabbitMqService;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqController {
    // 注入Spring Boot自定生成的对象
    @Autowired
    private RabbitMqService rabbitMqService = null;
    
    @GetMapping("/msg") // 字符串
    public Map<String, Object> msg(String message) {
        rabbitMqService.sendMsg(message);
        return resultMap("message", message);
    }
    
    @GetMapping("/user") // 用户
    public Map<String, Object> user(Long id, String userName, String note) {
        User user = new User(id, userName, note);
        rabbitMqService.sendUser(user);
        return resultMap("user", user);
    }
    // 结果Map
    private Map<String, Object> resultMap(String key, Object obj) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put(key, obj);
        return result;
    }
}
