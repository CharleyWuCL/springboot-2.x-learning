<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My WebSocket</title>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>
<script type="text/javascript" src="./../js/stomp.min.js"></script>
</head>
<script type="text/javascript">
	var noticeSocket = function() {
		var s = new SockJS('/wsuser');
		var stompClient = Stomp.over(s);
		stompClient.connect({}, function() {
			console.log('notice socket connected!');
			stompClient.subscribe('/user/queue/customer', function(data) {
				$('#receive').html(data.body);
			});
		});
	};
	noticeSocket();
</script>
<body>
<h1><span id="receive">等待接收消息</span></h1>
</body>
</html>