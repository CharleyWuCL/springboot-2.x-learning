<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CSRF FORM</title>
</head>
<body>
	<form action="./commit" method="post">
		<p>
			名称：<input id="name" name="name" type="text" value="" />
		</p>
		<p>
			描述：<input id="describe" name="describe" type="text" value="" />
		</p>
		<p>
		    <input type="submit" value="提交"/>
		</p>
		<!-- 
		<input type="hidden" id="${_csrf.parameterName}"
			name="${_csrf.parameterName}" value="${_csrf.token}" />
			  -->
	</form>
</body>
</html>