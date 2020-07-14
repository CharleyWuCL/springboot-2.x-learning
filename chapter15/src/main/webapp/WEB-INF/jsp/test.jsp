<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>购买产品测试</title>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<!--后面需要改写这段JavaScript脚本进行测试-->
<script type="text/javascript">
/*	
	var params = {
		userId : 1,
		productId : 1,
		quantity : 3
	};
	// 通过POST请求后端
	$.post("./purchase", params, function(result) {
		alert(result.message);
	});
*/	
	for (var i=1; i<=50000; i++) {
	    var params = {
	        userId : 1,
	        productId : 1,
	        quantity : 1
	    };
	    // 通过POST请求后端,这里的JavaScript会采用异步请求
	    $.post("./purchase", params, function(result) {
	    });
	}
</script>
<body>
	<h1>抢购产品测试</h1>
</body>
</html>