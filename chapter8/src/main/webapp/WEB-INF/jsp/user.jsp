<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello Spring Boot</title>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
<!--后面在此处加入JavaScript脚本-->
/*
var user = {
	id : 1,
	userName : 'user_name_for_update',
	note : 'note_for_update',
	roles : []
};
    
function post(user) {
	var url = "./update"
	$.post({
		url : url,
		// 此处需要告知传递参数类型为JSON，不能缺少
		contentType : "application/json",
		// 将JSON转化为字符串传递
		data : JSON.stringify(user),
		// 成功后的方法
		success : function(result, status) {
			if (result == null || result.id == null) {
				alert("插入失败");
				return;
			}
		}
	});
}

post(user);

*/

function post(user) {
	var url = "./save"
	$.post({
		url : url,
		// 此处需要告知传递参数类型为JSON，不能缺少
		contentType : "application/json",
		// 将JSON转化为字符串传递
		data : JSON.stringify(user),
		// 成功后的方法
		success : function(result, status) {
			if (result == null || result.id == null) {
				alert("插入失败");
				return;
			}
		}
	});
}
for (var i = 1; i <= 10; i++) {
	var user = {
		'id' : i,
		'userName' : 'user_name_' + i,
		'note' : "note_" + i,
		'roles' : [ {
			'id' : i,
			'roleName' : 'role_' + i,
			'note' : 'note_' + i
		}, {
			'id' : i + 1,
			'roleName' : 'role_' + (i + 1),
			'note' : 'note_' + (i + 1)
		} ]
	};
	post(user);
}

</script>
</head>
<body>
	<h1>操作MongoDB文档</h1>
</body>
</html>