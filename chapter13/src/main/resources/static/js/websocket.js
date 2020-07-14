var websocket = null;
// 判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
	// 创建WebSocket对象,连接服务器端点
	websocket = new WebSocket("ws://localhost:8080/ws");
} else {
	alert('Not support websocket')
}

// 连接发生错误的回调方法
websocket.onerror = function() {
	appendMessage("error");
};

// 连接成功建立的回调方法
websocket.onopen = function(event) {
	appendMessage("open");
}

// 接收到消息的回调方法
websocket.onmessage = function(event) {
	appendMessage(event.data);
}

// 连接关闭的回调方法
websocket.onclose = function() {
	appendMessage("close");
}

// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，
// 防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
	websocket.close();
}

// 将消息显示在网页上
function appendMessage(message) {
	var context = $("#context").html() +"<br/>" + message;
	$("#context").html(context);
}

// 关闭连接
function closeWebSocket() {
	websocket.close();
}

// 发送消息
function sendMessage() {
	var message = $("#message").val();
	websocket.send(message);
}