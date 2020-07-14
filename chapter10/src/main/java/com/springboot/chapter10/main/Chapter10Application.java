package com.springboot.chapter10.main;

import java.util.Locale;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication(scanBasePackages = "com.springboot.chapter10")
@MapperScan(basePackages = "com.springboot.chapter10", annotationClass = Mapper.class)
public class Chapter10Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Chapter10Application.class, args);
	}

	// 国际化拦截器
	private LocaleChangeInterceptor lci = null;

	// 国际化解析器，请注意这个Bean Name要为localeResolver
	@Bean(name = "localeResolver")
	public LocaleResolver initLocaleResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		// 默认国际化区域
		slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
		return slr;
	}

	// 创建国际化拦截器
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		if (lci != null) {
			return lci;
		}
		lci = new LocaleChangeInterceptor();
		// 设置参数名
		lci.setParamName("language");
		return lci;
	}

	// 给处理器增加国际化拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截器在执行处理器前方法(preHandle)将请求的国际区域根据参数修改为对应的区域
		registry.addInterceptor(localeChangeInterceptor());
	}

	// @Override
	// public void addInterceptors(InterceptorRegistry registry) {
	// // // 注册拦截器到Spring MVC机制，然后它会返回一个拦截器注册
	// // InterceptorRegistration ir = registry.addInterceptor(new Interceptor1());
	// // // 指定拦截匹配模式，限制拦截器拦截请求
	// // ir.addPathPatterns("/interceptor/*");
	//
	// // 注册拦截器到Spring MVC机制中
	// InterceptorRegistration ir = registry.addInterceptor(new
	// MulitiInterceptor1());
	// // 指定拦截匹配模式
	// ir.addPathPatterns("/interceptor/*");
	// // 注册拦截器到Spring MVC机制中
	// InterceptorRegistration ir2 = registry.addInterceptor(new
	// MulitiInterceptor2());
	// // 指定拦截匹配模式
	// ir2.addPathPatterns("/interceptor/*");
	// // 注册拦截器到Spring MVC机制中
	// InterceptorRegistration ir3 = registry.addInterceptor(new
	// MulitiInterceptor3());
	// // 指定拦截匹配模式
	// ir3.addPathPatterns("/interceptor/*");
	// }
}
