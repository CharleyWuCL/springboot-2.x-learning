<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="mvc" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title>Spring MVC国际化</title>
</head>
<body>
	<!-- 通过HTTP请求参数变化国际化 -->
	<a href="./page?language=zh_CN">简体中文</a>
	<a href="./page?language=en_US">美国英文</a>
	<h2>
		<!-- 找到属性文件变量名为welcome的配置 -->
		<spring:message code="msg" />
	</h2>
	<!-- 当前国际化区域 -->
	Locale: ${pageContext.response.locale }
</body>
</html>