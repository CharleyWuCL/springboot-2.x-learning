<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>格式化</title>
</head>
<body>
	<form action="./commit" method="post">
		<table>
			<tr>
				<td>日期（yyyy-MM-dd）</td>
				<td><input type="text" name="date" value="2017-08-08" /></td>
			</tr>
			<tr>
				<td>金额（#,###.##）</td>
				<td><input type="text" name="number" value="1,234,567.89" /></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="提交" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>