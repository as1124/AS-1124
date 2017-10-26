package com.mobile.thirdparty.access;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import com.eos.system.annotation.Bizlet;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.IWechatConstants;
import com.mobile.thirdparty.wechat.model.WechatAccessToken;
import com.mobile.thirdparty.wechat.model.WechatJSAccessToken;

/**
 * 获取token的工厂入口，方便扩展微信、支付宝的模型。
 * 
 * @author huangjw(huangjw@primeton.com)
 *
 */
public class AccessTokenFactory {

	/**
	 * 存放并管理token-manager
	 */
	private static Map<String, HashMap<String, AbstractAccessToken>> managerMap = new HashMap<String, HashMap<String,AbstractAccessToken>>();
	
	/**
	 * 向第三方平台请求获取接口调用票据对象。
	 * <pre>eg:获取微信接口调用token
	 * <p><code>
	 * List&lt;BasicNameValuePair> parameters = new ArrayList&lt;BasicNameValuePair>();
	 * parameters.add(new BasicNameValuePair("appid", appid));
	 * parameters.add(new BasicNameValuePair("secret", appSecret));
	 * parameters.add(new BasicNameValuePair("grant_type", "client_credential"));
	 * getToken(appid, parameters, WechatAccessToken.class);
	 * </code></p></pre>
	 * @param appid 唯一标识，可自定义，不能为空
	 * @param parameters 向第三方平台请求获取token时所需要的参数列表
	 * @param clazz token类型,this class must extends from {@link AbstractAccessToken}
	 * @return
	 * @throws ThirdPartyRequestExceprion 
	 */
	@Bizlet("getToken")
	@SuppressWarnings("unchecked")
	public static <T extends AbstractAccessToken> T getToken(String appid, List<BasicNameValuePair> parameters, 
			Class<? extends AbstractAccessToken> clazz) throws ThirdPartyRequestExceprion {
		HashMap<String, AbstractAccessToken> tokenMap = managerMap.get(clazz.getName());
		if(tokenMap == null){
			tokenMap = new HashMap<String, AbstractAccessToken>();
			managerMap.put(clazz.getName(), tokenMap);
		}
		
		AbstractAccessToken token = tokenMap.get(appid);
		if(token==null || token.isExpired() || token.getAccess_token().trim().equals("")){
			synchronized (tokenMap) {
				try {
					Class<?>[] type = new Class[0];
					Constructor<? extends AbstractAccessToken> at = clazz.getDeclaredConstructor(type);
					at.setAccessible(true);
					token = at.newInstance();
					token.initFields(parameters);
					tokenMap.put(appid, token);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} 
			}
		}
		return (T) token;
	}
	
	/**
	 * 向第三方平台请求获取接口调用票据对象
	 * <pre>eg:获取微信接口调用token
	 * <p><code>
	 * Map&lt;String, String> parameters = new HashMap&lt;String, String>();
	 * parameters.put("appid", appid);
	 * parameters.put("secret", appSecret);
	 * parameters.put("grant_type", "client_credential");
	 * getToken(appid, parameters, WechatAccessToken.class);
	 * </code></p></pre>
	 * @param appid 唯一标识，可自定义，不能为空
	 * @param parameters 向第三方平台请求获取token时所需要的参数, <code>Map&lt;key, value></code>
	 * @param tokenClass token类型,this class must extends from {@link AbstractAccessToken}
	 * @return
	 * @throws ThirdPartyRequestExceprion
	 */
	@Bizlet
	@SuppressWarnings("unchecked")
	public static <T extends AbstractAccessToken> T getToken(String appid, Map<String, String> parameters, 
			Class<? extends AbstractAccessToken> clazz) throws ThirdPartyRequestExceprion{
		HashMap<String, AbstractAccessToken> tokenMap = managerMap.get(clazz.getName());
		if(tokenMap == null){
			tokenMap = new HashMap<String, AbstractAccessToken>();
			managerMap.put(clazz.getName(), tokenMap);
		}
		
		AbstractAccessToken token = tokenMap.get(appid);
		if(token==null || token.isExpired() || token.getAccess_token().trim().equals("")){
			synchronized (tokenMap) {
				try {
					List<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>(parameters.size());
					Iterator<String> it = parameters.keySet().iterator();
					while(it.hasNext()){
						String key = it.next();
						param.add(new BasicNameValuePair(key, parameters.get(key)));
					}
					Class<?>[] type = new Class[0];
					Constructor<? extends AbstractAccessToken> at = clazz.getDeclaredConstructor(type);
					at.setAccessible(true);
					token = at.newInstance();
					token.initFields(param);
					tokenMap.put(appid, token);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} 
			}
		}
		return (T) token;
	}
	
	/**
	 * 获取微信公众号接口调用凭据
	 * @param appid 公众号appID
	 * @param appSecret 公众号appsecret
	 * @return
	 * @throws ThirdPartyRequestExceprion
	 */
	@Bizlet("getWechatToken")
	public static WechatAccessToken getWechatToken(String appid, String appSecret) throws ThirdPartyRequestExceprion {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("appid", appid));
		parameters.add(new BasicNameValuePair("secret", appSecret));
		parameters.add(new BasicNameValuePair("grant_type", "client_credential"));
		return getToken(appid, parameters, WechatAccessToken.class);
	}

	/**
	 * 通过微信网页授权获取网页接口调用的token
	 * @param appid 公众号appID
	 * @param appSecret 公众号appsecret
	 * @param code 微信网页授权返回的code码
	 * @return
	 * @throws ThirdPartyRequestExceprion 
	 */
	@Bizlet("getWechatWebAuthorizeToken")
	public static WechatJSAccessToken getWechatWebAuthorizeToken(String appid, String appSecret, String code) throws ThirdPartyRequestExceprion{
		List<BasicNameValuePair> queryStr = new ArrayList<BasicNameValuePair>();
		queryStr.add(new BasicNameValuePair("grant_type", "authorization_code"));
		queryStr.add(new BasicNameValuePair("code", code));
		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, appid));
		queryStr.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, appSecret));
		WechatJSAccessToken token = AccessTokenFactory.getToken(appid, queryStr, WechatJSAccessToken.class);
		return token;
	}
	
	/**
	 * 删除token对象
	 * @param uniqueID 唯一标识, 如果是微信token请传appid
	 */
	@Bizlet("removeToken")
	public static void removeToken(String uniqueID, Class<? extends AbstractAccessToken> clazz){
		if(managerMap.containsKey(clazz.getName())){
			managerMap.get(clazz.getName()).remove(uniqueID);
		}
	}

	public static void main(String[] args){
		try {
			Object o = getWechatToken("wxfad56e1d17a29e04", "480ae4fba743d9d7a832d00bbf3b8e97");
			System.out.println(o);
		} catch (ThirdPartyRequestExceprion e) {
			e.printStackTrace();
		}
	}
	
}
