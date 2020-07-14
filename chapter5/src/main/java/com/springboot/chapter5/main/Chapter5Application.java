package com.springboot.chapter5.main;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**** imports ****/
//定义Spring Boot扫描包路径
@SpringBootApplication(scanBasePackages = {"com.springboot.chapter5"})
//定义JPA接口扫描包路径
//@EnableJpaRepositories(basePackages = "com.springboot.chapter5.dao")
//定义实体Bean扫描包路径
//@EntityScan(basePackages = "com.springboot.chapter5.pojo")
@MapperScan(
	//指定扫描包
	basePackages = "com.springboot.chapter5.*",
	//指定SqlSessionFactory，如果sqlSessionTemplate被指定，则作废
	sqlSessionFactoryRef = "sqlSessionFactory",
	//指定sqlSessionTemplate，将忽略sqlSessionFactory的配置
	sqlSessionTemplateRef = "sqlSessionTemplate", 
	//markerInterface = Class.class,//限定扫描接口，不常用
	annotationClass = Mapper.class
)
public class Chapter5Application {
//	
//	@Bean
//	public MapperScannerConfigurer mapperScannerConfig() {
//		//定义扫描器实例
//		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//		//加载SqlSessionFactory,Spring Boot会自动生产，SqlSessionFactory实例，无需我们敢于
//		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//		//定义扫描的包
//		mapperScannerConfigurer.setBasePackage("com.springboot.chapter5.*");
//		//限定被标注@Repository的接口才被扫描
//		mapperScannerConfigurer.setAnnotationClass(Repository.class);
//		//通过继承某个接口限制扫描，一般使用不多
//		//mapperScannerConfigurer.setMarkerInterface(。。。。。。);
//		return mapperScannerConfigurer;
//	}
	
//	@Autowired
//	SqlSessionFactory sqlSessionFactory = null;
//	
//    @Bean 
//    public MapperFactoryBean<MyBatisUserDao> initMyBatisUserDao() {
//    	MapperFactoryBean<MyBatisUserDao> bean = new MapperFactoryBean<>();
//    	bean.setMapperInterface(MyBatisUserDao.class);
//    	bean.setSqlSessionFactory(sqlSessionFactory);
//    	return bean;
//    }
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Chapter5Application.class, args);
	}
	
	
}