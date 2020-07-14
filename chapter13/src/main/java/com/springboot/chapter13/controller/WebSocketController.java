package com.springboot.chapter13.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**** imports ****/
@Controller
@RequestMapping("/websocket")
public class WebSocketController {
    // 跳转websocket页面
    @GetMapping("/index")
    public String websocket() {
        return "websocket";
    }
    
    @Autowired // 注入Spring Boot自动配置消息模板对象
    private SimpMessagingTemplate simpMessagingTemplate;
    
    // 发送页面
    @GetMapping("/send")
    public String send() {
        return "send";
    }
    
    // 接收页面
    @GetMapping("/receive")
    public String receive() {
        return "receive";
    }
    
    // 对特定用户发送页面
    @GetMapping("/sendUser")
    public String sendUser() {
        return "send-user";
    }
    
    // 接收用户消息页面
    @GetMapping("/receiveUser")
    public String receiveUser() {
        return "receive-user";
    }
    
    // 定义消息请求路径
    @MessageMapping("/send")
    // 定义结果发送到特定路径
    @SendTo("/sub/chat")
    public String sendMsg(String value) {
         return value;
    }
    
    // 将消息发送给特定用户
    @MessageMapping("/sendUser")
    public void sendToUser(Principal principal, String body) {
        String srcUser = principal.getName();
        // 解析用户和消息
        String []args = body.split(",");
        String desUser = args[0];
        String message = "【" + srcUser + "】给你发来消息：" + args[1];
        // 发送到用户和监听地址
        simpMessagingTemplate.convertAndSendToUser(desUser, 
            "/queue/customer", message);    
    }
}