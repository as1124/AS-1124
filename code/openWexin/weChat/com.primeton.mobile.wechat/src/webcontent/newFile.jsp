<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- 
  - Author(s): huangjw
  - Date: 2015-05-13 10:55:11
  - Description:
-->
<head>
<title>测试微信</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="<%= request.getContextPath() %>/common/nui/nui.js" type="text/javascript"></script>
    
</head>
<body>

<a  class="nui-button" onclick="getAccessToken">
   获取AccessToekn
</a>
<br>
<p></p>
<a  class="nui-button" onclick="getUserInfo">
    拿到用户信息
</a>
<br>
<p></p>
<img alt="" src="" id="headImg">
<br><br><br><br><br>

<form class="" id="form1" action="aaa.jsp" method="post" enctype="multipart/form-data" target="myFrame">
<input class="nui-htmlfile" limitType="*.jpg" />
</form>
<iframe id="fram" name="myFrame">

</iframe>
<a  class="nui-button" onclick="submit" iconCls="icon-edit">
    提交
</a>

<script type="text/javascript">
nui.parse();
var accessToken = "";
<%
String encoding = request.getCharacterEncoding();
out.println(encoding);
 %>
function submit(){
document.getElementById("form1").submit();

}


function submitData(){
	var form = nui.get("form1");
	nui.ajax({
        url:'com.primeton.mobile.weChat.UserComponent.getAccessToken.biz.ext',
        type:'GET',
        data:{
        	'accessToken':'6SBATTcp3QXX2dg5QrZllpVluO2dvHVPE8F9Cwtbl6bizsfbmZc1bDc6u8-vM3KhL9HWmwcI2yU21jrFHKECgzrrWCNIUVda9Gu82oA5kt8',
			'type':'image',
			'form':form
        },
        success:function(text){
             accessToken = text;
             nui.alert(accessToken.accessToken);
        },
		error: function(err){
			alert("出错");
		}
	});
}

function getAccessToken(){
	nui.ajax({
        url:'com.primeton.mobile.weChat.UserComponent.getAccessToken.biz.ext',
        type:'GET',
        data:{
        	'appID':'wxc70c5d9aab4f6a2b',
			'appSecret':'f3ca23ccf76c301f2158862db65cfdad'
        },
        success:function(text){
             accessToken = text;
             nui.alert(accessToken.accessToken);
        },
		error: function(err){
			alert("出错");
		}
	});
}

var userInfo = "";
function getUserInfo(){
	nui.ajax({
        url:'com.primeton.mobile.weChat.UserComponent.getUserInfo.biz.ext',
        type:'GET',
        contentType:"text/json",
        data:{
        	'accessToken':accessToken.accessToken,
        	'openID':'oB1fEuFzGh6e2EMg5Ac5c9xugaRQ'
        },
        success:function(text){
             userInfo = JSON.parse(text.user);
             document.getElementById("headImg").src = userInfo.headimgurl;
           
        },
		error: function(err){
			alert("出错");
		}
	});

}
</script>
</body>
</html>