package com.springboot.chapter11.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.springboot.chapter11.pojo.User;
import com.springboot.chapter11.vo.UserVo;

public class RestTemplateMain {

	public static void main(String[] args) {
//		getUser(3L);
//		findUser("u", "n", 0, 10);
		UserVo vo = new UserVo();
		vo.setUserName("user_name_vo");
		vo.setNote("user_name_note");
		vo.setSexCode(0);
		vo.setSexName("男");
		insertUserEntity(vo);
//		deleteUser(12L);
		useExchange(vo, 13L);
	}
	
	// 获取用户
	public static UserVo getUser(Long id) {
	    // 创建一个RestTemplate对象
	    RestTemplate restTmpl = new RestTemplate();
	    // 消费服务，第一个参数为url，第二个是返回类型，第三个是URI路径参数
	    UserVo userVo = restTmpl.getForObject(
	            "http://localhost:8080/user/{id}", UserVo.class, id);
	    // 打印用户名称
	    System.out.println(userVo.getUserName());
	    return userVo;
	}
	
	public static List<UserVo> findUser(String userName, 
	        String note, int start, int limit) {
	    RestTemplate restTmpl = new RestTemplate();
	    // 使用Map进行封装多个参数，以提高可读性
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("userName", "user");
	    params.put("note", "note");
	    params.put("start", start);
	    params.put("limit", limit);
	    // Map中的key和URI中的参数一一对应
	    String url = "http://localhost:8080/users/{userName}/{note}/{start}/{limit}"; 
	    // 请求后端
	    ResponseEntity<List> responseEntity = restTmpl.getForEntity(url, List.class, params);
	    List<UserVo> userVoes = responseEntity.getBody();
	    return userVoes;
	}
	
	// 新增用户
	public static User insertUser(UserVo newUserVo) {
	    // 请求头
		HttpHeaders headers = new HttpHeaders();
	    // 设置请求内容为JSON类型
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	    // 创建请求实体对象
	    HttpEntity<UserVo> request = new HttpEntity<>(newUserVo, headers);
	    RestTemplate restTmpl = new RestTemplate();
	    // 请求时传递请求实体对象，并返回回填id的用户
	    User user = restTmpl.postForObject("http://localhost:8080/user", request, User.class);
	    System.out.println(user.getId());
	    return user;
	}
	
	public static void deleteUser(Long id) {
	    RestTemplate restTmpl = new RestTemplate();
	    restTmpl.delete("http://localhost:8080/user/{id}", id);
	}

	public static User insertUserEntity(UserVo newUserVo) {
	    // 请求头
	    HttpHeaders headers = new HttpHeaders();
	    // 请求类型
	    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	    // 绑定请求体和头
	    HttpEntity<UserVo> request = new HttpEntity<>(newUserVo, headers);
	    RestTemplate restTmpl = new RestTemplate();
	    // 请求服务器
	    ResponseEntity<User> userEntity = restTmpl.postForEntity(
	        "http://localhost:8080/user2/entity", request, User.class);
	    // 获取响应体
	    User user = userEntity.getBody();
	    // 获取响应头
	    HttpHeaders respHeaders = userEntity.getHeaders();
	    // 获取响应属性
	    List<String> success = respHeaders.get("success");
	    // 响应的HTTP状态码
	    int status = userEntity.getStatusCodeValue();
	    System.out.println(user.getId());
	    return user;
	}
	
	public static User useExchange(UserVo newUserVo, Long id) {
	    // 请求头
	    HttpHeaders headers = new HttpHeaders();
	    // 请求类型
	    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	    // 绑定请求体和头
	    HttpEntity<UserVo> request = new HttpEntity<>(newUserVo, headers);
	    RestTemplate restTmpl = new RestTemplate();
	    String url = "http://localhost:8080/user2/entity";
	    // 请求服务器
	    ResponseEntity<User> userEntity 
	        = restTmpl.exchange(url, HttpMethod.POST, request, User.class);
	    // 获取响应体
	    User user = userEntity.getBody();
	    // 获取响应头
	    HttpHeaders respHeaders = userEntity.getHeaders();
	    // 响应头属性
	    List<String> success = respHeaders.get("success");
	    // 响应的HTTP状态码
	    int status = userEntity.getStatusCodeValue();
	    System.out.println(user.getId());
	    // 修改URL获取资源
	    url = "http://localhost:8080/user/{id}";
	    // 传递URL地址参数
	    ResponseEntity<UserVo> userVoEntity 
	        = restTmpl.exchange(url, HttpMethod.GET, null, UserVo.class, id);
	    // 获取响应体
	    UserVo userVo = userVoEntity.getBody();
	    System.out.println(userVo.getUserName());
	    return user;
	}
}
