package com.priemton.mobile.thirdparty.access;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import com.primeton.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;

/**
 * 获取token的工厂入口，方便扩展微信、支付宝的模型。
 * 
 * @author huangjw(huangjw@primeton.com)
 *
 */
public class AccessTokenFactory {

	private static Map<String, AbstractAccessToken> tokenMap = new HashMap<String, AbstractAccessToken>();
	
	/**
	 * 向第三方平台请求获取接口调用票据
	 * 
	 * @param uniqueID 区别标识，可自定义
	 * @param parameters 向第三方平台请求获取token所需要的参数
	 * @param clazz
	 * @return
	 * @throws ThirdPartyRequestExceprion 
	 */
	@SuppressWarnings("unchecked")
	public static <T extends AbstractAccessToken> T getToken(String uniqueID, List<BasicNameValuePair> parameters, 
			Class<? extends AbstractAccessToken> clazz) throws ThirdPartyRequestExceprion {
		AbstractAccessToken token = tokenMap.get(uniqueID);
		if(token==null || token.isExpired() || token.getAccess_token().trim().equals("")){
			synchronized (tokenMap) {
				try {
					Class<?>[] type = new Class[0];
					Constructor<? extends AbstractAccessToken> at = clazz.getDeclaredConstructor(type);
					at.setAccessible(true);
					token = at.newInstance();
					token.initFields(parameters);
					tokenMap.put(uniqueID, token);
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
	 * 向第三方平台请求获取接口调用票据
	 * 
	 * @param uniqueID 区别标识，可自定义
	 * @param parameters 向第三方平台请求获取token所需要的参数, <code>Map&lt;key, value></code>
	 * @param tokenClass this class must extends from {@link AbstractAccessToken}
	 * @return
	 * @throws ThirdPartyRequestExceprion
	 */
	@SuppressWarnings("unchecked")
	public static <T extends AbstractAccessToken> T getToken(String uniqueID, Map<String, String> parameters, String tokenClass) 
			throws ThirdPartyRequestExceprion{
		AbstractAccessToken token = tokenMap.get(uniqueID);
		if(token==null || token.isExpired() || token.getAccess_token().trim().equals("")){
			synchronized (tokenMap) {
				try {
					Class<?> clazz = Class.forName(tokenClass);
					if(AbstractAccessToken.class.isAssignableFrom(clazz) == false){
						ClassCastException e = new ClassCastException(tokenClass + " can not cast to" + AbstractAccessToken.class.getName());
						throw e;
					} 
					List<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>(parameters.size());
					Iterator<String> it = parameters.keySet().iterator();
					while(it.hasNext()){
						String key = it.next();
						param.add(new BasicNameValuePair(key, parameters.get(key)));
					}
					Class<?>[] type = new Class[0];
					Constructor<? extends AbstractAccessToken> at = (Constructor<? extends AbstractAccessToken>) clazz.getDeclaredConstructor(type);
					at.setAccessible(true);
					token = at.newInstance();
					token.initFields(param);
					tokenMap.put(uniqueID, token);
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
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 
			}
		}
		return (T) token;
	}
	
	public static void removeToken(String uniqueID){
		if(tokenMap.containsKey(uniqueID)){
			tokenMap.remove(uniqueID);
		}
	}
	
}
