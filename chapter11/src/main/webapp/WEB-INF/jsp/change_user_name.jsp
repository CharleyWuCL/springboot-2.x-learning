<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表单定义HTTP动作</title>
</head>
<body>
	<form id="form" action="./name" method="post">
		<table>
			<tr>
				<td>用户编号</td>
				<td><input id="id" name="id" /></td>
			</tr>
			<tr>
				<td>用户名称</td>
				<td><input id="userName" name="userName" /></td>
			</tr>
			<tr>
				<td></td>
				<td align="right"><input id="submit" name="submit"
					type="submit" /></td>
			</tr>
		</table>
		<input type="hidden" name="_method" id="_method" value="PATCH" />
	</form>
</body>
</html>