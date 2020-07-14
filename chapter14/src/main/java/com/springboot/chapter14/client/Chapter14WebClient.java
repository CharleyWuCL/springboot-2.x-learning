package com.springboot.chapter14.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.springboot.chapter14.client.pojo.UserPojo;
import com.springboot.chapter14.pojo.User;
import com.springboot.chapter14.vo.UserVo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**** imports ****/
public class Chapter14WebClient {
	public static void main(String[] args) {
		// 创建WebClient对象，并且设置请求基础路径
		WebClient client = WebClient.create("http://localhost:8080");
		getSecurityUser(client, 1L);
//		updateUserName(client, 1L, "update_user_name");
//		getUserPojo(client, 1L);
//		// 一个新的用户
//		User newUser = new User();
//		newUser.setId(6L);
//		newUser.setNote("note_6");
//		newUser.setUserName("user_name_6");
//		newUser.setSex(SexEnum.MALE);
//		insertUser3(client, newUser);
//		insertUser2(client);
//		// 一个新的用户
//		User newUser = new User();
//		newUser.setId(1L);
//		newUser.setNote("note_1");
//		newUser.setUserName("user_name_1");
//		newUser.setSex(SexEnum.MALE);
//		// 新增用户
//		insertUser(client, newUser);
//		 // 获取用户
//		 getUser(client, 1L);
//		 User updUser = new User();
//		 updUser.setId(1L);
//		 updUser.setNote("note_update");
//		 updUser.setUserName("user_name_update");
//		 updUser.setSex(SexEnum.FEMALE);
//		 // 更新用户
//		 updateUser(client, updUser);
//		 // 查询用户
//		 findUsers(client, "user", "note");
//		 // 删除用户
//		 deleteUser(client, 3L);

	}

	private static void insertUser(WebClient client, User newUser) {
		// 注意这只是定义一个时间，并不会发送请求
		Mono<UserVo> userMono =
				// 定义POST请求
				client.post()
						// 设置请求URI
						.uri("/user")
						// 请求体为JSON数据流
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						// 请求体内容
						.body(Mono.just(newUser), User.class)
						// 接收请求结果类型
						.accept(MediaType.APPLICATION_STREAM_JSON)
						// 设置请求结果检索规则
						.retrieve()
						// 将结果体转换为一个Mono封装的数据流
						.bodyToMono(UserVo.class);
		// 获取服务器发布的数据流，此时才会发送请求
		UserVo user = userMono.block();
		System.out.println("【用户名称】" + user.getUserName());
	}
	
	
	private static void insertUser3(WebClient client, User newUser) {
		// 注意这只是定义一个时间，并不会发送请求
		Mono<UserVo> userMono =
				// 定义POST请求
				client.post()
						// 设置请求URI
						.uri("/user3")
						// 请求体为JSON数据流
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						// 请求体内容
						.body(Mono.just(newUser), User.class)
						// 接收请求结果类型
						.accept(MediaType.APPLICATION_STREAM_JSON)
						// 设置请求结果检索规则
						.retrieve()
						// 将结果体转换为一个Mono封装的数据流
						.bodyToMono(UserVo.class);
		// 获取服务器发布的数据流，此时才会发送请求
		UserVo user = userMono.block();
		System.out.println("【用户名称】" + user.getUserName());
	}

	private static void getUser(WebClient client, Long id) {
		Mono<UserVo> userMono =
				// 定义GET请求
				client.get()
						// 定义请求URI和参数
						.uri("/user/{id}", id)
						// 接收请求结果类型
						.accept(MediaType.APPLICATION_STREAM_JSON)
						// 设置请求结果检索规则
						.retrieve()
						// 将结果体转换为一个Mono封装的数据流
						.bodyToMono(UserVo.class);
		// 获取服务器发布的数据流，此时才会发送请求
		UserVo user = userMono.block();
		System.out.println("【用户名称】" + user.getUserName());
	}

	private static void updateUser(WebClient client, User updUser) {
		Mono<UserVo> userMono =
				// 定义PUT请求
				client.put().uri("/user")
						// 请求体为JSON数据流
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						// 请求体内容
						.body(Mono.just(updUser), User.class)
						// 接收请求结果类型
						.accept(MediaType.APPLICATION_STREAM_JSON)
						// 设置请求结果检索规则
						.retrieve()
						// 将结果体转换为一个Mono封装的数据流
						.bodyToMono(UserVo.class);
		// 获取服务器发布的数据流，此时才会发送请求
		UserVo user = userMono.block();
		System.out.println("【用户名称】" + user.getUserName());
	}

	private static void findUsers(WebClient client, String userName, String note) {
		// 定义参数map
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("note", note);
		Flux<UserVo> userFlux =
				// 定义PUT请求，使用Map传递多个参数
				client.get().uri("/user/{userName}/{note}", paramMap)
						// 接收请求结果类型
						.accept(MediaType.APPLICATION_STREAM_JSON)
						// 设置请求结果检索规则
						.retrieve()
						// 将结果体转换为一个Mono封装的数据流
						.bodyToFlux(UserVo.class);

		// 通过Iterator遍历结果数据流，执行后服务器才会响应
		Iterator<UserVo> iterator = userFlux.toIterable().iterator();
		// 遍历
		while (iterator.hasNext()) {
			UserVo item = iterator.next();
			System.out.println("【用户名称】" + item.getUserName());
		}
	}

	private static void deleteUser(WebClient client, Long id) {
		Mono<Void> result = client.delete()
				// 设置请求URI
				.uri("/user/{id}", id)
				// 接收请求结果类型
				.accept(MediaType.APPLICATION_STREAM_JSON)
				// 设置请求结果检索规则
				.retrieve()
				// 将结果体转换为一个Mono封装的数据流
				.bodyToMono(Void.class);
		// 获取服务器发布的数据流，此时才会发送请求
		Void voidResult = result.block();
		System.out.println(voidResult);
	}

	private static void insertUser2(WebClient client) {
	    // 注意这只是定义一个时间，并不会发送请求
	    Mono<UserVo> userMono = 
	        // 定义POST请求
	        client.post()
	        // 设置请求URI，和约定格式的用户信息
	        .uri("/user2/{user}", "2-convert4-0-note4")
	        // 接收请求结果类型
	        .accept(MediaType.APPLICATION_STREAM_JSON)
	        // 设置请求结果检索规则
	        .retrieve()
	        // 将结果体转换为一个Mono封装的数据流
	        .bodyToMono(UserVo.class);
	    // 获取服务器发布的数据流，此时才会发送请求
	    UserVo user = userMono.block();
	    System.out.println("【用户名称】" + user.getUserName());
	}
	
	public static void getUser2(WebClient client, Long id) {
	    Mono<UserVo> userMono =
	        // HTTP GET请求
	        client.get()
	            // 定义请求URI和参数
	            .uri("/user/{id}", id)
	            // 接收结果为JSON数据流
	            .accept(MediaType.APPLICATION_STREAM_JSON)
	            // 设置检索
	            .retrieve().onStatus(
	                // 发生4开头或者5开头的状态码，4开头是客户端错误，5开头是服务器错误
	                // 第一个Lambda表达式，返回如果为true，则执行第二个Lambda表达式
	                status -> status.is4xxClientError() || status.is5xxServerError(),
	                // 如果发生异常，则用第二个表达式返回作为结果
	                // 第二个Lambda表达式
	                response -> Mono.empty())
	                // 将请求结果转换为Mono数据流
	            .bodyToMono(UserVo.class);
	    UserVo user = userMono.block();
	    // 如果用户正常返回
	    if (user != null) {
	        System.out.println("【用户名称】" + user.getUserName());
	    } else {// 不能正常返回或者用户为空
	        System.out.println("服务器没有返回编号为：" + id + "的用户");
	    }
	}
	
	// 转换方法
	private static UserPojo translate(UserVo vo) {
	    if (vo == null) {
	        return null;
	    }
	    UserPojo pojo = new UserPojo();
	    pojo.setId(vo.getId());
	    pojo.setUserName(vo.getUserName());
	    // 性别转换
	    pojo.setSex(vo.getSexCode() == 1 ? 1 : 2);
	    pojo.setNote(vo.getNote());
	    return pojo;
	}

	public static void getUserPojo(WebClient client, Long id) {
	    Mono<UserPojo> userMono =
	        // HTTP GET请求
	        client.get()
	             // 定义请求URI和参数
	            .uri("/user/{id}", id)
	             // 接收结果为JSON数据流
	            .accept(MediaType.APPLICATION_STREAM_JSON)
	             // 启用交换
	            .exchange()
	             // 出现错误则返回空
	            .doOnError(ex -> Mono.empty())
	             // 获取服务器发送过来的UserVo对象
	            .flatMap(response -> response.bodyToMono(UserVo.class))
	             // 通过自定义方法转换为客户端的UserPojo
	            .map(user -> translate(user));
	    // 获取客户端的UserPojo
	    UserPojo pojo = userMono.block();
	    // 不为空打印信息
	    if (pojo != null) {
	        System.out.println("获取的用户名称为" + pojo.getUserName());
	    } else {
	        System.out.println("获取的用户编号为" + id + "失败");
	    }
	}
	
	public static void updateUserName(WebClient client, Long id, String userName) {
	    Mono<UserVo> monoUserVo = client
	         // HTTP PUT请求 
	        .put()
	         // 请求URI
	        .uri("/user/name", userName)
	         // 第一个请求头
	        .header("id", id +"")
	         // 第二个请求头
	        .header("userName", userName)
	         // 设置接收JSON数据流
	        .accept(MediaType.APPLICATION_STREAM_JSON)
	         // 检索
	        .retrieve()
	         // 根据服务端响应码处理逻辑
	        .onStatus(
	            status -> status.is4xxClientError() || status.is5xxServerError(), 
	            response -> Mono.empty())
	         // 转换为UserVo对象
	        .bodyToMono(UserVo.class);
	    UserVo userVo = monoUserVo.block();
	    // 不为空打印信息
	    if (userVo != null) {
	        System.out.println("获取的用户名称为" + userVo.getUserName());
	    } else {
	        System.out.println("获取的用户编号为" + id + "失败");
	    }
	}
	
	public static void getSecurityUser(WebClient client, Long id) {
	    Mono<UserVo> monoUserVo = client
	         // HTTP PUT请求 
	        .get()
	         // 请求URI
	        .uri("/security/user/{id}", id)
	         // 第一个请求头
	        .header("header_password", "pwd")
	         // 第二个请求头
	        .header("header_user", "user")
	         // 设置接收JSON数据流
	        .accept(MediaType.APPLICATION_STREAM_JSON)
	         // 检索
	        .retrieve()
	         // 根据服务端响应码处理逻辑
	        .onStatus(
	            status -> status.is4xxClientError() || status.is5xxServerError(), 
	            response -> Mono.empty())
	         // 转换为UserVo对象
	        .bodyToMono(UserVo.class);
	    UserVo userVo = monoUserVo.block();
	    // 不为空打印信息
	    if (userVo != null) {
	        System.out.println("获取的用户名称为" + userVo.getUserName());
	    } else {
	        System.out.println("获取的用户编号为" + id + "失败");
	    }
	}
}