<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义登录表单</title>
</head>
<body>
    <form action="/login/page" method="POST">
        <p>名称：<input id="username" name="username" type="text" value=""/></p>
        <p>描述：<input id="password" name="password" type="password" value=""/></p>
        <p>记住我：<input id="remember_me" name="remember-me" type="checkbox"></p> 
        <p><input type="submit" value="登录"></p>
        <input type="hidden" id="${_csrf.parameterName}" 
            name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</body>
</html>