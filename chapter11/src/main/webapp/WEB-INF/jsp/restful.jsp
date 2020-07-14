<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello Spring Boot</title>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	/**
	 function post() {
	 var params = {
	 'userName': 'user_name_new', 
	 'sexCode' : 1,
	 'note' : "note_new"
	 }
	 $.post({
	 url : "./user",
	 // 此处需要告知传递参数类型为JSON，不能缺少
	 contentType : "application/json",
	 // 将JSON转化为字符串传递
	 data : JSON.stringify(params),
	 // 成功后的方法
	 success : function(result) {
	 if (result == null || result.id == null) {
	 alert("插入失败");
	 return;
	 }
	 alert("插入成功");
	 }
	 });
	 }
	 post();
	 */
	/**
	 function get() {
	 $.get("./user/1", function(user, status) {
	 if (user == null) {
	 alert("结果为空")
	 } else {
	 alert("用户信息为"+JSON.stringify(user));
	 }
	 });
	 }
	 get();
	 */
	/*
	 function findUsers() {
	 $.get("./users/u/n/0/5", function(user, status) {
	 if (user == null) {
	 alert("结果为空")
	 } else {
	 alert("用户信息为:"+JSON.stringify(user));
	 }
	 });
	 }
	 findUsers();
	 */
	 /*
	function updateUser() {
		var params = {
			'userName' : 'user_name_1_update',
			'sexCode' : 1,
			'note' : "note_new_1"
		}
		$.ajax({
			url : "./user/1",
			// 此处告知使用PUT请求
			type : 'PUT',
			// 此处需要告知传递参数类型为JSON，不能缺少
			contentType : "application/json",
			// 将JSON转化为字符串传递
			data : JSON.stringify(params),
			success : function(user, status) {
				if (user == null) {
					alert("结果为空")
				} else {
					alert(JSON.stringify(user));
				}
			}
		});
	}

	updateUser();
	*/
	/*
	function updateUserName() {
	    $.ajax({url:"./user/1/user_name_patch", 
	        type:"PATCH", 
	        success: function(result, status) {
	            if (result == null) {
	                   alert("结果为空")
	            } else {
	                alert(result.success? "更新成功" : "更新失败");
	            }
	        }
	    })
	}
	updateUserName();
	*/
	/*
	function deleteUser() {
	    $.ajax({
	        url : "./user/1", 
	        type :'DELETE', 
	        success : function(result) {
	        if (result == null) {
	            alert("结果为空")
	        } else {
	            alert(result.success? "删除成功" : "删除失败");
	        }
	    }});
	}
	deleteUser();
	*/
	function postStatus() {
	    // 请求体
	    var params = {
	        'userName': 'user_name_new', 
	        'sexCode' : 1,
	        'note' : "note_new"
	    }
	    var url = "./user2/annotation";
	    // var url = "./user2/annotation";
	    $.post({
	        url : url,
	        // 此处需要告知传递参数类型为JSON，不能缺少
	        contentType : "application/json",
	        // 将JSON转化为字符串传递
	        data : JSON.stringify(params),
	        // 成功后的方法
	        success : function(result, status, jqXHR) {
	            // 获取响应头
	            var success = jqXHR.getResponseHeader("success");
	            // 获取状态码
	            var status = jqXHR.status;
	            alert("响应头参数是：" + success+"，状态码是：" + status);
	            if (result == null || result.id == null) {
	                alert("插入失败");
	                return;
	            }
	            alert("插入成功");
	        }
	    });
	}
	postStatus();
</script>
</head>
<body>
	<h1>测试RESTful下的请求</h1>
</body>
</html>