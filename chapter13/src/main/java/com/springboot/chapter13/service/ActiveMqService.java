package com.springboot.chapter13.service;
// ActiveMQ服务接口
public interface ActiveMqService {
    
    // 发送消息
    public void sendMsg(String message);
    
    // 接收消息
    public void receiveMsg(String message); 
}