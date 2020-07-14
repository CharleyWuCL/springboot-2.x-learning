<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>用户详情</title>
    </head>
    <body>
    
        <center>
            <table border="1">
                <tr>
                    <td>标签</td>
                    <td>值</td>
                </tr>
                <tr>
                    <td>用户编号</td>
                    <td><c:out value="${user.id}"></c:out></td>
                </tr>
                <tr>
                    <td>用户名称</td>
                    <td><c:out value="${user.userName}"></c:out></td>
                </tr>
                <tr>
                    <td>用户备注</td>
                    <td><c:out value="${user.note}"></c:out></td>
                </tr>
            </table>
        </center>
    </body>
</html>