<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>获取请求头参数</title>
<!-- 加载Query文件-->
<script src="https://code.jquery.com/jquery-3.2.0.js">
</script>
<script type="text/javascript">
$.post({
    url : "./user",
    // 设置请求头参数
    headers : {id : '1'},
    // 成功后的方法
    success : function(user) {
        if (user == null || user.id == null) {
            alert("获取失败");
            return;
        }
        // 弹出请求返回的用户信息
        alert("id=" + user.id +", user_name="
        		+user.userName+", note="+ user.note);
    }
});
</script>
</head>
<body>
</body>