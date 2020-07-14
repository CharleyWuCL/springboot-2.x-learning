package com.springboot.chapter13.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.chapter13.pojo.User;
import com.springboot.chapter13.service.ActiveMqService;
import com.springboot.chapter13.service.ActiveMqUserService;

/**** imports ****/
@Controller
@RequestMapping("/activemq")
public class ActiveMqController {
    
    // 注入服务对象
    @Autowired
    private ActiveMqService activeMqService = null;
    
    // 注入服务对象
    @Autowired
    private ActiveMqUserService activeMqUserService = null;
    
    // 测试普通消息的发送
    @ResponseBody
    @GetMapping("/msg")
    public Map<String, Object> msg(String message) {
        activeMqService.sendMsg(message);
        return result(true, message);
    }
    
    // 测试User对象的发送
    @ResponseBody
    @GetMapping("/user")
    public Map<String, Object> sendUser(Long id,
            String userName, String note) {
        User user = new User(id, userName, note);
        activeMqUserService.sendUser(user);
        return result(true, user);
        
    }
    
    private Map<String, Object> result(Boolean success, Object message) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", message);
        return result;
    }
}