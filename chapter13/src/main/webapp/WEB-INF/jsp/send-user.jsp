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
<script type="text/javascript" src="./../js/stomp.min.js"></script>
</head>
<script type="text/javascript">
    var stompClient = null;
    // 重置连接状态页面
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
    // 连接/wsuser服务端点
        var socket = new SockJS('/wsuser');
        // stomp客户端
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
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
    // 向‘/request/sendUser’服务端发送消息
    function sendMsg() {
        var value = $("#message").val();
        var user = $("#user").val();
        // 用户和消息组成的字符串
        var text = user +"," + value;
        stompClient.send("/request/sendUser", {}, text);
    }
    connect();
</script>
<body>
    <div>
        <div>
            <button id="connect" onclick="connect();">连接</button>
            <button id="disconnect" disabled="disabled" onclick="disconnect();">断开连接</button>
        </div>
        <div id="conversationDiv">
            <p><label>发送给用户</label></p>
            <p><input type="text" id="user"/></p>
            <p><label>发送的内容</label></p>
            <p><textarea id="message" rows="5"></textarea></p>
            <button id="sendMsg" onclick="sendMsg();">发送</button>
            <p id="response"></p>
        </div>
    </div>
</body>
</html>