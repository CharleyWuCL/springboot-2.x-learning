<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户列表</title>
<link rel="stylesheet" type="text/css"
	href="../../easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../../easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../easyui/demo/demo.css">
<script type="text/javascript" src="../../easyui/jquery.min.js"></script>
<script type="text/javascript" src="../../easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
	// 定义事件方法
	function onSearch() {
		// 指定请求路径
		var opts = $("#dg").datagrid("options");
		opts.url = "./list";
		// 获取查询参数
		var userName = $("#userName").val();
		var note = $("#note").val();
		// 组织参数
		var params = {};
		if (userName != null && userName.trim() != '') {
			params.userName = userName;
		}
		if (note != null && note.trim() != '') {
			params.note = note;
		}
		// 重新载入表格数据
		$("#dg").datagrid('load', params);
	}
</script>
</head>
<body>
	<div style="margin: 20px 0;"></div>
	<div class="easyui-layout" style="width: 100%; height: 350px;">
		<div data-options="region:'north'" style="height: 50px">
			<form id="searchForm" method="post">
				<table>
					<tr>
						<td>用户名称：</td>
						<td><input id="userName" name="userName"
							class="easyui-textbox" data-options="prompt:'输入用户名称...'"
							style="width: 100%; height: 32px"></td>
						<td>备注</td>
						<td><input id="note" name="note" class="easyui-textbox"
							data-options="prompt:'输入备注...'" style="width: 100%; height: 32px">
						</td>
						<td><a href="#" class="easyui-linkbutton"
							data-options="iconCls:'icon-search'" style="width: 80px"
							onclick="onSearch()">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',title:'用户列表',iconCls:'icon-ok'">
			<table id="dg" class="easyui-datagrid"
				,
data-options="border:false,singleSelect:true,
fit:true,fitColumns:true">
				<thead>
					<tr>
						<th data-options="field:'id'" width="80">编号</th>
						<th data-options="field:'userName'" width="100">用户名称</th>
						<th data-options="field:'note'" width="80">备注</th>
					</tr>
				</thead>
				<tbody>
					<!--使用forEache渲染数据模型-->
					<c:forEach items="${userList}" var="user">
						<tr>
							<td>${user.id}</td>
							<td>${user.userName}</td>
							<td>${user.note}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>