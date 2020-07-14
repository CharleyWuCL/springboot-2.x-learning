package com.springboot.chapter12.main;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter12")
// 使用注解驱动缓存机制
@EnableCaching
@MapperScan(basePackages = "com.springboot.chapter12", annotationClass = Mapper.class)
public class Chapter12Application extends WebSecurityConfigurerAdapter {

	// 注入数据源
	@Autowired
	private DataSource dataSource = null;

	// 使用用户名称查询密码
	String pwdQuery = " select user_name, pwd, available" + " from t_user where user_name = ?";
	// 使用用户名称查询角色信息
	String roleQuery = " select u.user_name, r.role_name " + " from t_user u, t_user_role ur, t_role r "
			+ "where u.id = ur.user_id and r.id = ur.role_id" + " and u.user_name = ?";

	@Value("${system.user.password.secret}")
	private String secret = null;

	@Autowired
	private UserDetailsService UserDetailsService = null;

	/**
	 * 覆盖WebSecurityConfigurerAdapter用户详情方法
	 * 
	 * @param auth
	 *            用户签名管理器构造器
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(this.secret);
		auth.userDetailsService(UserDetailsService).passwordEncoder(passwordEncoder);
	}

	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//	    http .httpBasic().realmName("my-basic-name").and()
//	    // 访问/admin下的请求需要管理员权限
//	     .authorizeRequests().antMatchers("/admin/**").access("hasRole('ADMIN')")
//	    // 启用remember me功能
//	    .and().rememberMe().tokenValiditySeconds(86400).key("remember-me-key")
//	    // 启用HTTP Batic功能
//	    .and().httpBasic()
//	    // 通过签名后可以访问任何请求
//	    .and().authorizeRequests().antMatchers("/**").permitAll()
//	    // 设置登录页和默认的跳转路径
//	    .and().formLogin().loginPage("/login/page").defaultSuccessUrl("/admin/welcome1");
//	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	        // 访问/admin下的请求需要管理员权限
	        .authorizeRequests().antMatchers("/admin/**")
	            .access("hasRole('ADMIN')")
	        // 通过签名后可以访问任何请求
	        .and().authorizeRequests()
	            .antMatchers("/**").permitAll()
	        // 设置登录页和默认的跳转路径
	        .and().formLogin().loginPage("/login/page")
	            .defaultSuccessUrl("/admin/welcome1")
	        // 登出页面和默认跳转路径
	        .and().logout().logoutUrl("/logout/page")
	            .logoutSuccessUrl("/welcome");
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
		// // 限定签名后的权限
		// http.
		// /* ########第一段######## */
		// authorizeRequests()
		// // 限定"/user/welcome"请求赋予角色ROLE_USER或者ROLE_ADMIN
		// .antMatchers("/user/welcome", "/user/details").hasAnyRole("USER", "ADMIN")
		// // 限定"/admin/"下所有请求权限赋予角色ROLE_ADMIN
		// .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
		// // 其他路径允许签名后访问
		// .anyRequest().permitAll()
		//
		// /* ######## 第二段 ######## */
		// /** and代表连接词 **/
		// // 对于没有配置权限的其他请求允许匿名访问
		// .and().anonymous()
		//
		// /* ######## 第三段 ######## */
		// // 使用Spring Security默认的登录页面
		// .and().formLogin()
		// // 启动HTTP基础验证
		// .and().httpBasic();
//		http.authorizeRequests().regexMatchers("/user/welcome", "/user/details").hasAnyRole("USER", "ADMIN")
//				.regexMatchers("/admin/.*").hasAuthority("ROLE_ADMIN").and().formLogin().and().httpBasic();
//		http.csrf().disable().authorizeRequests()
//		// 使用Spring表达式限定只有角色ROLE_USER或者ROLE_ADMIN
//		.antMatchers("/user/**").access("hasRole('USER') or hasRole('ADMIN')")
//		 // 设置访问权限给角色ROLE_ADMIN，要求是完整登录(非记住我登录)
//		.antMatchers("/admin/welcome1").
//		    access("hasAuthority('ROLE_ADMIN') && isFullyAuthenticated()")
//	    // 限定"/admin/welcome2"访问权限给角色ROLE_ADMIN，允许不完整登录
//		.antMatchers("/admin/welcome2").access("hasAuthority('ROLE_ADMIN')")
//		// 使用记住我的功能
//		.and().rememberMe()
//		// 使用Spring Security默认的登录页面
//		.and().formLogin()
//		// 启动HTTP基础验证
//		.and().httpBasic();
//	}

	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws Exception
	// {
	// PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(this.secret);
	// auth.jdbcAuthentication()
	// // 密码编码器
	// .passwordEncoder(passwordEncoder)
	// // 数据源
	// .dataSource(dataSource)
	// // 查询用户，自动判断密码是否一致
	// .usersByUsernameQuery(pwdQuery)
	// // 赋予权限
	// .authoritiesByUsernameQuery(roleQuery);
	// }

	@Autowired
	private RedisTemplate redisTemplate = null;

	@PostConstruct
	public void initRedisTemplate() {
		RedisSerializer<String> strSerializer = redisTemplate.getStringSerializer();
		redisTemplate.setKeySerializer(strSerializer);
		redisTemplate.setHashKeySerializer(strSerializer);
	}

	// protected void configure(AuthenticationManagerBuilder auth) throws Exception
	// {
	// // 密码编码器
	// PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	// auth.jdbcAuthentication()
	// //密码编码器
	// .passwordEncoder(passwordEncoder)
	// // 数据源
	// .dataSource(dataSource)
	// // 查询用户，自动判断密码是否一致
	// .usersByUsernameQuery(pwdQuery)
	// // 赋予权限
	// .authoritiesByUsernameQuery(roleQuery);
	// }

	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws Exception
	// {
	// // 密码编码器
	// PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	// // 使用内存存储
	// InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> userConfig
	// = auth.inMemoryAuthentication()
	// // 设置密码编码器
	// .passwordEncoder(passwordEncoder);
	// // 注册用户admin，密码为abc,并赋予USER和ADMIN的角色权限
	// userConfig.withUser("admin")
	// // 可通过passwordEncoder.encode("abc")得到加密后的密码
	// .password("$2a$10$5OpFvQlTIbM9Bx2pfbKVzurdQXL9zndm1SrAjEkPyIuCcZ7CqR6je")
	// .authorities("ROLE_USER", "ROLE_ADMIN");
	//
	// // 注册用户myuser，密码为123456,并赋予USER的角色权限
	// userConfig.withUser("myuser")
	// // 可通过passwordEncoder.encode("123456")得到加密后的密码
	// .password("$2a$10$ezW1uns4ZV63FgCLiFHJqOI6oR6jaaPYn33jNrxnkHZ.ayAFmfzLS")
	// .roles("ROLE_USER");
	// }

	public static void main(String[] args) {
		SpringApplication.run(Chapter12Application.class, args);
		// PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder("uvwxyz");
		// String p1 = passwordEncoder.encode("abc");
		// String p2 = passwordEncoder.encode("123456");
		// System.out.println(p1);
		// System.out.println(p2);
	}
}
