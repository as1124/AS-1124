<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Spittr</title>
</head>
<body>
	<h1>Welcome to Spring</h1>
	<form id="spitter" action="/springstudy/spring/user/registx" method="POST">
		User-ID: <input id="id" name="id" type="text" /> <br /> 
		User-Name: <input id="userName" name="userName" type="text" /> <br />
		Birthday: <input id="birthday" name="birthday" type="text" /> <br />
		Address: <input id="address" name="address" type="text" /> <br />
		<input type="submit" value="Register" />
	</form>
	<%
		out.write("哈哈哈哈，This is JSP\r\n");
	%>

	<br /> ${result}
</body>
</html>

