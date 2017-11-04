/**
 * 根据Code 请求微信网页授权凭证 access_token
 * 并返回数据
 * @returns
 */
function getWXAccessToken(){
	var openid = getCookie("openid");
	if(!openid || openid == ""){
		//如果openid不存在  或者是 测试openid则调用api重新获取openid
		$.ajax({
			url: '/syx/rest/wechat/js_token',
			async: false,
			type: "POST",
			data:{
				code: getQueryString("code")
			},
			dataType: 'json',
			success: function(data){
				if(data){
					console.log(data);
					//attention 请求失败时 设置openid为 '' 方便本地调试
//					data.result = data.result || {openid: "",expires_in: 7200};  
//					
//					callbackData = data.result;
//					setCookie("openid", data.result.openid,data.result.expires_in);
				} else {
					alert("请求数据出错");
					console.err("请求数据出错");
				}
			},
			error: function() {
				alert("请求服务器出错！");
			}
		});
	}
	return {'openid' : '12345'};
}
