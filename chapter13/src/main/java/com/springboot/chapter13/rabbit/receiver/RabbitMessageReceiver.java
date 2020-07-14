package com.springboot.chapter13.rabbit.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.springboot.chapter13.pojo.User;

@Component
public class RabbitMessageReceiver {
	
	// 定义监听字符串队列名称
	@RabbitListener(queues = { "${rabbitmq.queue.msg}" })
	public void receiveMsg(String msg) {
 		System.out.println("收到消息: 【" + msg + "】");
	}
	
    // 定义监听用户队列名称
	@RabbitListener(queues = { "${rabbitmq.queue.user}" })
	public void receiveUser(User user) {
		System.out.println("收到用户信息【" + user + "】");
	}
}
