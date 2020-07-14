<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 加载Query文件-->
<script src="https://code.jquery.com/jquery-3.2.0.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    // 请求shutdown端点
    $.get({
        url : "./manage/beans",
        // 成功后的方法
        success : function(result) {
        	// 检测请求结果
            if (result != null || result.message != null) {
            	// 打印消息
                alert(result.key1);
                return;
            }
            alert("请求失败");
        }
    });
});
</script>
<title>测试监控端点</title>
</head>
<body>
</body>
</html>