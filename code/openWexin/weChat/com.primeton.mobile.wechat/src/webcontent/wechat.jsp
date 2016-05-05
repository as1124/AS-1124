<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,java.io.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试微信</title>
</head>
<body>
HHHHHHHH
黄家伟
<%
String encoding = request.getCharacterEncoding();
out.print(encoding);
String signature = request.getParameter("signature");
System.out.println(signature); 
// 时间戳  
String timestamp = request.getParameter("timestamp");
System.out.println(timestamp);
// 随机数  
String nonce = request.getParameter("nonce");
System.out.println(nonce);
// 随机字符串  
String echostr = request.getParameter("echostr");
System.out.println(echostr);
 
PrintWriter outWriter = response.getWriter();  
outWriter.print(echostr);
 %>
</body>
</html>