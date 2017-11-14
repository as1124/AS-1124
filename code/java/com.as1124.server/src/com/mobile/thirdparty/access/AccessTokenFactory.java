package com.mobile.thirdparty.access;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mobile.common.util.LoggerUtil;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;

/**
 * 获取token的工厂入口，方便扩展微信、支付宝的模型。
 * 
 * @author huangjw(as1124huang@gmail.com)
 *
 */
public class AccessTokenFactory {

	static Logger logger = LoggerUtil.getLogger(AccessTokenFactory.class);

	private AccessTokenFactory() {
	}

	/**
	 * 存放并管理token-manager
	 */
	private static Map<String, HashMap<String, AbstractAccessToken>> managerMap = new HashMap<>();

	/**
	 * 向第三方平台请求获取接口调用票据对象。
	 * 
	 * <pre>
	 * eg:获取微信接口调用token
	 * <code>
	 * List&lt;BasicNameValuePair> parameters = new ArrayList&lt;BasicNameValuePair>();
	 * parameters.add(new BasicNameValuePair("appid", appid));
	 * parameters.add(new BasicNameValuePair("secret", appSecret));
	 * parameters.add(new BasicNameValuePair("grant_type", "client_credential"));
	 * getToken(appid, parameters, WechatAccessToken.class);
	 * </code>
	 * </pre>
	 * 
	 * @param appid
	 *            唯一标识，可自定义，不能为空
	 * @param parameters
	 *            向第三方平台请求获取token时所需要的参数列表
	 * @param clazz
	 *            token类型, this class must extends from {@link AbstractAccessToken}
	 * @return
	 * @throws ThirdPartyRequestExceprion
	 */
	@SuppressWarnings("unchecked")
	public static <T extends AbstractAccessToken> T getToken(String appid, List<NameValuePair> parameters,
			Class<? extends AbstractAccessToken> clazz) throws ThirdPartyRequestExceprion {
		HashMap<String, AbstractAccessToken> tokenMap = managerMap.get(clazz.getName());
		if (tokenMap == null) {
			tokenMap = new HashMap<>();
			managerMap.put(clazz.getName(), tokenMap);
		}

		AbstractAccessToken token = tokenMap.get(appid);
		if (token == null || token.isExpired() || StringUtils.isBlank(token.getAccessToken())) {
			synchronized (tokenMap) {
				try {
					Class<?>[] type = new Class[0];
					Constructor<? extends AbstractAccessToken> atConstructor = clazz.getDeclaredConstructor(type);
					atConstructor.setAccessible(true);
					token = atConstructor.newInstance();
					token.initFields(parameters);
					tokenMap.put(appid, token);
				} catch (Exception e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
					ThirdPartyRequestExceprion ex = new ThirdPartyRequestExceprion(e.getMessage());
					ex.initCause(e);
					throw ex;
				}
			}
			token.setClientID(appid);
		}
		return (T) token;
	}

	/**
	 * 向第三方平台请求获取接口调用票据对象
	 * 
	 * <pre>
	 * eg:获取微信接口调用token
	 * <code>
	 * Map&lt;String, String> parameters = new HashMap&lt;String, String>();
	 * parameters.put("appid", appid);
	 * parameters.put("secret", appSecret);
	 * parameters.put("grant_type", "client_credential");
	 * getToken(appid, parameters, WechatAccessToken.class);
	 * </code>
	 * </pre>
	 * 
	 * @param appid
	 *            唯一标识，可自定义，不能为空
	 * @param parameters
	 *            向第三方平台请求获取token时所需要的参数, <code>Map&lt;key, value></code>
	 * @param tokenClass
	 *            token类型, this class must extends from {@link AbstractAccessToken}
	 * @return
	 * @throws ThirdPartyRequestExceprion
	 */
	@SuppressWarnings("unchecked")
	public static <T extends AbstractAccessToken> T getToken(String appid, Map<String, String> parameters,
			Class<? extends AbstractAccessToken> clazz) throws ThirdPartyRequestExceprion {
		HashMap<String, AbstractAccessToken> tokenMap = managerMap.get(clazz.getName());
		if (tokenMap == null) {
			tokenMap = new HashMap<>();
			managerMap.put(clazz.getName(), tokenMap);
		}

		AbstractAccessToken token = tokenMap.get(appid);
		if (token == null || token.isExpired() || StringUtils.isBlank(token.getAccessToken())) {
			synchronized (tokenMap) {
				List<NameValuePair> param = new ArrayList<>(parameters.size());
				Iterator<String> it = parameters.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					param.add(new BasicNameValuePair(key, parameters.get(key)));
				}
				token = getToken(appid, param, clazz);
			}
		}
		return (T) token;
	}

	/**
	 * 删除token对象
	 * 
	 * @param uniqueID
	 *            唯一标识, 如果是微信token请传appid
	 */
	public static void removeToken(String uniqueID, Class<? extends AbstractAccessToken> clazz) {
		if (managerMap.containsKey(clazz.getName())) {
			managerMap.get(clazz.getName()).remove(uniqueID);
		}
	}

}
