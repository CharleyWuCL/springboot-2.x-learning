package com.springboot.chapter13.main;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter13")
@EnableScheduling
public class Chapter13Application extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Chapter13Application.class, args);
	}

    // 定义3个可以登录的内存用户
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 密码加密器
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加入三个内存用户，密码分别为加密后的"p1","p2"和"p3"
		// 可以通过 passwordEncoder.encode("p1")这样获得加密后的密码
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
            .withUser("user1")
            .password("$2a$10$7njFQKL2WV862XP6Hlyly.F0lkSHtOOQyQ/rlY7Ok26h.gGZD4IqG").roles("USER").and()
            .withUser("user2").password("$2a$10$Q2PwvWNpog5sZX583LuQfet.y1rfPMsqtrb7IjmvRn7Ew/wNUjVwS")
            .roles("ADMIN").and().withUser("user3")
            .password("$2a$10$GskYZT.34BdhmEdOlAS8Re7D73RprpGN0NjaiqS2Ud8XdcBcJck4u").roles("USER");
    }

	// 消息队列名称
	@Value("${rabbitmq.queue.msg}")
	private String msgQueueName = null;

	// 用户队列名称
	@Value("${rabbitmq.queue.user}")
	private String userQueueName = null;

	@Bean
	public Queue createQueueMsg() {
		// 创建字符串消息队列，boolean值代表是否持久化消息
		return new Queue(msgQueueName, true);
	}

	@Bean
	public Queue createQueueUser() {
		// 创建用户消息队列，boolean值代表是否持久化消息
		return new Queue(userQueueName, true);
	}

}
