package com.springboot.chapter7.main;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter7")
//指定扫描的MyBatis Mapper
@MapperScan(basePackages = "com.springboot.chapter7", annotationClass = Repository.class)
//使用注解驱动缓存机制
@EnableCaching
public class Chapter7Application {


	// 设置RedisTemplate的序列化器
	private void initRedisTemplate() {
		RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
	}

	// RedisTemplate
	@Autowired
	private RedisTemplate redisTemplate = null;
	
	@Autowired
	private RedisConnectionFactory connectionFactory = null;
	// 自定义初始化方法
	@PostConstruct
	public void init() {
	    initRedisTemplate();
	}
	
	
	@Bean(name = "redisCacheManager" )
	public RedisCacheManager initRedisCacheManager() {
		// Redis加锁的写入器
		RedisCacheWriter writer= RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
		// 启动Redis缓存的默认设置
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		// 设置JDK序列化器
		config = config.serializeValuesWith(SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()));
		// 禁用前缀
		config = config.disableKeyPrefix();
		//设置10分钟超时
		config = config.entryTtl(Duration.ofMinutes(10));
		// 创建缓Redis存管理器
		RedisCacheManager redisCacheManager = new RedisCacheManager(writer, config);
		return redisCacheManager;
	}
	
	

//	// Redis连接工厂
//	@Autowired
//	private RedisConnectionFactory connectionFactory = null;
//
//	// Redis消息监听器
//	@Autowired
//	private MessageListener redisMsgListener = null;
//
//	// 任务池
//	private ThreadPoolTaskScheduler taskScheduler = null;
//
//	/**
//	 * 创建任务池，运行线程等待处理Redis的消息
//	 * 
//	 * @return
//	 */
//	@Bean
//	public ThreadPoolTaskScheduler initTaskScheduler() {
//		if (taskScheduler != null) {
//			return taskScheduler;
//		}
//		taskScheduler = new ThreadPoolTaskScheduler();
//		taskScheduler.setPoolSize(20);
//		return taskScheduler;
//	}
//
//	/**
//	 * 定义Redis的监听容器
//	 * 
//	 * @return 监听容器
//	 */
//	@Bean
//	public RedisMessageListenerContainer initRedisContainer() {
//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//		// Redis连接工厂
//		container.setConnectionFactory(connectionFactory);
//		// 设置运行任务池
//		container.setTaskExecutor(initTaskScheduler());
//		// 定义监听渠道，名称为topic1
//		Topic topic = new ChannelTopic("topic1");
//		// 使用监听器监听Redis的消息
//		container.addMessageListener(redisMsgListener, topic);
//		return container;
//	}
//
	public static void main(String[] args) {
		SpringApplication.run(Chapter7Application.class, args);
	}

}
