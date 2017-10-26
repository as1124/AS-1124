package com.mobile.thirdparty.wechat.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.IWechatConstants;

/**
 * 微信网页授权access_token模型，
 * access_token有效期一般是<code>7200s</code>，refresh_token的有效期是30天
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatJSAccessToken extends WechatAccessToken {

	/**
	 * refresh_token的创建时间
	 */
	private long refreshCreateTime = 0;
	
	/**
	 * 网页授权后去的用户openid
	 */
	private String openid;
	
	/* （非 Javadoc）
	 * @see com.primeton.mobile.thirdparty.wechat.model.WechatAccessToken#initFields(java.util.List)
	 */
	protected void initFields(List<BasicNameValuePair> parameters)
			throws ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/sns/oauth2/access_token";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
//		queryStr.add(new BasicNameValuePair("grant_type", "authorization_code"));
//		queryStr.add(new BasicNameValuePair("code", code));
//		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, appID));
//		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, appSecret));
		queryStr.addAll(parameters);
		JSONObject json = JSONObject.parseObject(HttpExecuter.executeGetAsString(uri, queryStr));
		String err = json.getString(IWechatConstants.ERROR_CODE);
		if(err==null || err.equals(IWechatConstants.RETURN_CODE_SUCCESS)){
			this.createTime = System.currentTimeMillis();
			this.refreshCreateTime = this.createTime;
			this.access_token = json.getString(IWechatConstants.KEY_ACCESS_TOKEN);
			this.expires_in = json.getLong("expires_in");
			this.refresh_token = json.getString("refresh_token");
			this.openid = json.getString("openid");
			this.scope = json.getString("scope");
		} else {
			String errMsg = json.getString(IWechatConstants.ERROR_MSG);
			ThirdPartyRequestExceprion exp = new ThirdPartyRequestExceprion("[errCode="+err+"], "+errMsg);
			throw exp;
		}
	}
	
	public boolean isExpired() {
		return true;
	}
	
	protected void refreshAccessToken(List<BasicNameValuePair> parameters) throws ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("grant_type", "refresh_token"));
		queryStr.add(new BasicNameValuePair("refresh_token", this.refresh_token));
		for(BasicNameValuePair param : parameters){
			String key = param.getName();
			if(key.equals(IWechatConstants.KEY_APP_ID)){
				queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, param.getValue()));
				break;
			}
		}
		
		JSONObject json = JSONObject.parseObject(HttpExecuter.executeGetAsString(uri, queryStr));
		String err = json.getString(IWechatConstants.ERROR_CODE);
		if(err==null || err.equals(IWechatConstants.RETURN_CODE_SUCCESS)){
			this.createTime = System.currentTimeMillis();
			this.access_token = json.getString(IWechatConstants.KEY_ACCESS_TOKEN);
			this.expires_in = json.getLong("expires_in");
			this.refresh_token = json.getString("refresh_token");
			this.openid = json.getString("openid");
			this.scope = json.getString("scope");
		} else {
			String errMsg = json.getString(IWechatConstants.ERROR_MSG);
			ThirdPartyRequestExceprion exp = new ThirdPartyRequestExceprion("[errCode="+err+"], "+errMsg);
			throw exp;
		}
	}

	/**
	 * @see #openid
	 * @return openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid 设置 <code>{@link #openid}</code>字段的值
	 * @see #openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @see #refreshCreateTime
	 * @return refresh_create_time
	 */
	protected long getRefreshCreateTime() {
		return refreshCreateTime;
	}

	/**
	 * @param refresh_create_time 设置 <code>{@link #refreshCreateTime}</code>字段的值
	 * @see #refresh_create_time
	 */
	protected void setRefreshCreateTime(long refresh_create_time) {
		this.refreshCreateTime = refresh_create_time;
	}
	
}
