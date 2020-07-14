package com.springboot.chapter14.config;


import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import com.springboot.chapter14.enumeration.SexEnum;
import com.springboot.chapter14.pojo.User;

/**** imports ****/
// 实现Java 8的接口WebFluxConfigurer，该接口都是default方法
@Configuration
public class WebFluxConfig implements WebFluxConfigurer {
    // 注册Converter
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToUserConverter());
    }

    // 定义String --> User类型转换器
    // @Bean// 如果定义为Spring Bean，Spring Boot会自动识别为类型转换器
    public Converter<String, User> stringToUserConverter() {
        Converter<String, User> converter = new Converter<String, User>() {
            @Override
            public User convert(String src) {
                String strArr[] = src.split("-");
                User user = new User();
                Long id = Long.valueOf(strArr[0]);
                user.setId(id);
                user.setUserName(strArr[1]);
                int sexCode = Integer.valueOf(strArr[2]);
                SexEnum sex = SexEnum.getSexEnum(sexCode);
                user.setSex(sex);
                user.setNote(strArr[3]);
                return user;
            }
        };
        return converter;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            // 注册资源，可以通过URI访问
            .addResourceHandler("/resources/static/**")
            // 注册Spring资源，可以在Spring机制中访问
            .addResourceLocations("/public/**", "classpath:/static/")
            // 缓存一年(365天)
            .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }
    
//    // 设置全局性验证器
//    @Override
//    public Validator getValidator() {
//        return new UserValidator();
//    }
}