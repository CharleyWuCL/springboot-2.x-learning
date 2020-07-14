package com.springboot.chapter14.config;

//静态导入
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springboot.chapter14.handler.UserHandler;

import reactor.core.publisher.Mono;

/**** other packages import ****/
@Configuration
public class RouterConfig {
	// 注入用户处理器
	@Autowired
	private UserHandler userHandler = null;

	// 请求头用户名属性名称
	private static final String HEADER_NAME = "header_user";
	// 请求头密码属性名称
	private static final String HEADER_VALUE = "header_password";

	@Bean
	public RouterFunction<ServerResponse> securityRouter() {
		RouterFunction<ServerResponse> router =
				// 对应请求URI的对应关系
				route(
						// GET请求和其路径
						GET("/security/user/{id}")
								// 响应结果为JSON数据流
								.and(accept(APPLICATION_STREAM_JSON)),
						// 定义处理方法
						userHandler::getUser)
								// 使用过滤器
								.filter((request, next) -> filterLogic(request, next));
		return router;
	}

	// 用户路由
	@Bean
	public RouterFunction<ServerResponse> userRouter() {
		RouterFunction<ServerResponse> router =
				// 对应请求URI的对应关系
				route(
						// GET请求和其路径
						GET("/router/user/{id}")
								// 响应结果为JSON数据流
								.and(accept(APPLICATION_STREAM_JSON)),
						// 定义处理方法
						userHandler::getUser)
								// 增加一个路由
								.andRoute(
										// GET请求和其路径
										GET("/router/user/{userName}/{note}").and(accept(APPLICATION_STREAM_JSON)),
										// 定义处理方法
										userHandler::findUsers)
								// 增加一个路由
								.andRoute(
										// POST请求和其路径
										POST("/router/user")
												// 请求体为JSON数据流
												.and(contentType(APPLICATION_STREAM_JSON)
														// 响应结果为JSON数据流
														.and(accept(APPLICATION_STREAM_JSON))),
										// 定义处理方法
										userHandler::insertUser)
								// 增加一个路由
								.andRoute(
										// PUT请求和其路径
										PUT("/router/user")
												// 请求体为JSON数据流
												.and(contentType(APPLICATION_STREAM_JSON))
												// 响应结果为JSON数据流
												.and(accept(APPLICATION_STREAM_JSON)),
										// 定义处理方法
										userHandler::updateUser)
								.andRoute(
										// DELETE请求和其路径
										DELETE("/router/user/{id}")
												// 响应结果为JSON数据流
												.and(accept(APPLICATION_STREAM_JSON)),
										// 定义处理方法
										userHandler::deleteUser)
								.andRoute(
										// PUT请求和其路径
										PUT("/router/user/name")
												// 响应结果为JSON数据流
												.and(accept(APPLICATION_STREAM_JSON)),
										// 定义处理方法
										userHandler::updateUserName);
		return router;
	}

	// 请求过滤器逻辑
	private Mono<ServerResponse> filterLogic(ServerRequest request, HandlerFunction<ServerResponse> next) {
		// 取出请求头
		String userName = request.headers().header(HEADER_NAME).get(0);
		String password = request.headers().header(HEADER_VALUE).get(0);
		// 验证通过的条件
		if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password) && !userName.equals(password)) {
			// 接受请求
			return next.handle(request);
		}
		// 请求头不匹配，则不允许请求，返回为未签名错误
		return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
	}
}