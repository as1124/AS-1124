package com.as1124.server.wxsapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.as1124.server.wxsapp.access.WechatPlatformConstants;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.access.IAccessToken;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.model.WechatJSAccessToken;

/**
 * 微信资源请求Restful Service
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */
@Path("/wechat")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class WechatService {

	@POST
	@Path("/js_token")
	@Produces("application/json; charset=UTF-8")
	public Response getJSToken(@FormParam("code") String code) {
		Object result = new String("{}");
		try {
			result = WechatJSAccessToken.getWechatWebAuthorizeToken(WechatPlatformConstants.SYX_APPID, WechatPlatformConstants.SYX_APP_SECRET,
				code);
		} catch (ThirdPartyRequestExceprion e) {
			e.printStackTrace();
		}
		return Response.status(Status.OK).entity(result).build();
	}

	@POST
	@Path("/userinfo")
	@Produces("application/json; charset=UTF-8")
	public Response getUserInfo(String openid) {
		String result = "{}";
		try {
			IAccessToken token = WechatJSAccessToken.getWechatWebAuthorizeToken(WechatPlatformConstants.SYX_APPID,
				WechatPlatformConstants.SYX_APP_SECRET, "");
			String url = "https://api.weixin.qq.com/sns/userinfo";
			List<NameValuePair> queryStr = new ArrayList<>();
			queryStr.add(new BasicNameValuePair("access_token", token.getAccessToken()));
			queryStr.add(new BasicNameValuePair("openid", openid));
			queryStr.add(new BasicNameValuePair("lang", "zh_CN"));
			result = HttpExecuter.executeGetAsString(url, queryStr);
		} catch (ThirdPartyRequestExceprion e) {
			e.printStackTrace();
		}
		return Response.status(Status.OK).entity(result).build();
	}

}
