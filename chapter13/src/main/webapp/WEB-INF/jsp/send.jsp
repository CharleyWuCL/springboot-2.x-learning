<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My WebSocket</title>
<script type="text/javascript" 
    src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" 
    src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script> 
<!--
stomp.min.js的下载地址：
https://raw.githubusercontent.com/jmesnil/stomp-websocket/master/lib/stomp.min.js
该地址设定为文本，所以不能直接载入，需要自行先下载，再使用
-->
<script type="text/javascript" src="./../js/stomp.min.js"></script>
</head>
<script type="text/javascript">
	var stompClient = null;
     // 设置连接
	function setConnected(connected) {
		$("#connect").attr({"disabled": connected});
		$("#disconnect").attr({"disabled": !connected});
		
		if (connected) {
		    $("#conversationDiv").show();
		} else {
			$("#conversationDiv").hide();
		}
		$("#response").html("");
	} 
	
	// 开启socket连接 
	function connect() {
          // 定义请求服务器的端点
		var socket = new SockJS('/socket');
          // stomp客户端
		stompClient = Stomp.over(socket);
          // 连接服务器端点
		stompClient.connect({}, function(frame) {
               // 建立连接后的回调
			setConnected(true);
		});
	}
	// 断开socket连接
	function disconnect() {
		if (stompClient != null) {
			stompClient.disconnect();
		}
		setConnected(false);
		console.log("Disconnected");
	}
	// 向‘/request/send’服务端发送消息
	function sendMsg() {
		var value = $("#message").val();
          // 发送消息到"/request/send",其中/request是服务器定义的前缀，
// 而/send则是@MessageMapping所配置的路径
		stompClient.send("/request/send", {}, value);
	}
	connect();
</script>

<body>
	<div>
		<div>
			<button id="connect" onclick="connect();">连接</button>
			<button id="disconnect" disabled="disabled" 
			onclick="disconnect();">断开连接</button>
		</div>
		<div id="conversationDiv">
			<p>
				<label>发送的内容</label>
			</p>
			<p>
				<textarea id="message" rows="5"></textarea>
			</p>
			<button id="sendMsg" onclick="sendMsg();">Send</button>
			<p id="response"></p>
		</div>
	</div>
</body>
</html>