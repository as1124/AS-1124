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
	<form id="spitter" action="/user/register" method="POST">
		First Name: <input id="firstName" name="firstName" type="text" /> <br />
		Last Name: <input id="lastName" name="lastName" type="text" /> <br />
		Email: <input id="email" name="email" type="text" /> <br />
		Username: <input id="username" name="username" type="text" /> <br />
		Password: <input id="password" name="password" type="password" /> <br />
		<input type="submit" value="Register" />
	</form>
	<%
		out.write("哈哈哈哈，This is JSP\r\n");
	%>

	<br />
	
	${result}
</body>
</html>

