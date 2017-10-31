package com.mobile.thirdparty.wechat;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.AbstractAccessToken;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.model.WechatGrammer;

/**
 * Wechat 智能操作接口.
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@Deprecated
public class AdvanceOperations {
	
	/**
	 * 创建语义
	 * @param token
	 * @param queryStr 语义搜索串, 即用户输入的文本串
	 * @param category 需要使用的服务类型，多个用“，”隔开，不能为空
	 * @param city 城市名称
	 * @param region 区域名称
	 * @param appid 公众号唯一标识i
	 * @param uid 用户唯一id（非开发者id），用户区分公众号下的不同用户（建议填入用户openid），
	 * 如果为空，则无法使用上下文理解功能。appid和uid同时存在的情况下，才可以使用上下文理解功能。
	 * @return
	 * @throws IOException 
	 * @throws ThirdPartyRequestExceprion 
	 */
	public WechatGrammer createGrammer(AbstractAccessToken token, String queryStr, String category, 
			String city, String appid, String uid) throws ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/semantic/semproxy/search";
		ArrayList<NameValuePair> apiQueryStr = new ArrayList<NameValuePair>();
		apiQueryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("query", queryStr);
		jsonObj.put("category", category);
		jsonObj.put("city", city);
		jsonObj.put("appid", appid);
		jsonObj.put("uid", uid);
		StringEntity requestEntity = new StringEntity(jsonObj.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, apiQueryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatGrammer.class);
		}
		else throw new ThirdPartyRequestExceprion("[AdvanceOperations#createGrammer]"+result);
	}
	
}
