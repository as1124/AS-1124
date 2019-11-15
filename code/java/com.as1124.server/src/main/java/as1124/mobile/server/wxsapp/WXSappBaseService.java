package as1124.mobile.server.wxsapp;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.AbstractAccessToken;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.model.WechatAccessToken;

/**
 * 微信小程序开放能力对应的后台接口
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class WXSappBaseService {

	private WXSappBaseService() {
		// default constructor
	}

	/**
	 * 微信小程序用户登录，通过客户端微信授权登录的code换取session_key<br/>
	 * 包含可用字段：<code>openid, session_key, unionid</code>
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static JSONObject login(String appid, String appSecret, String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("appid", appid));
		params.add(new BasicNameValuePair("SECRET", appSecret));
		params.add(new BasicNameValuePair("js_code", code));
		params.add(new BasicNameValuePair("js_code", "authorization_code"));
		String result = HttpExecuter.executeGetAsString(url, params);
		if (StringUtils.isNotBlank(result)) {
			return JSON.parseObject(result);
		} else {
			return null;
		}
	}

	/**
	 * 获取微信小程序接口调用凭证
	 * @param appid
	 * @param appSecret
	 * @return
	 * @throws ThirdPartyRequestExceprion
	 */
	public static WechatAccessToken getToken(String appid, String appSecret) throws ThirdPartyRequestExceprion {
		return WechatAccessToken.getWechatToken(appid, appSecret);
	}

	/**
	 * 用户支付完成后，获取该用户的<code> unionid </code>
	 */
	public static void getUserInfo(AbstractAccessToken apiToken, String openid) {
		String url = " https://api.weixin.qq.com/wxa/getpaidunionid";
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("access_token", apiToken.getAccessToken()));
		params.add(new BasicNameValuePair("openid", openid));
		String result = HttpExecuter.executeGetAsString(url, params);
		if (StringUtils.isNotBlank(result)) {
		} else {
		}
	}

	/**
	 * 解密小程序授权后获得的用户加密数据
	 * @param encryptData 加密信息
	 * @param sessionKey 会话Key，也就是加解密的密钥
	 * @param iv 初始向量
	 * @return
	 */
	public static String decryptUserInfo(String encryptData, String sessionKey, String iv) {
		if (StringUtils.isBlank(encryptData) || StringUtils.isBlank(sessionKey) || StringUtils.isBlank(iv)) {
			return "{}";
		}
		byte[] secretKey = Base64.decodeBase64(sessionKey);
		try {
			Cipher cipher = Cipher.getInstance("AES_128/CBC/NOPADDING");
			Key cipherKey = new SecretKeySpec(secretKey, "AES");
			IvParameterSpec ivParam = new IvParameterSpec(Base64.decodeBase64(iv));
			AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
			params.init(ivParam);
			cipher.init(Cipher.DECRYPT_MODE, cipherKey, params);
			String temp = new String(cipher.update(Base64.decodeBase64(encryptData)));
			return temp.substring(0, temp.lastIndexOf('}'));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// 算法名称有误
		} catch (InvalidAlgorithmParameterException | InvalidKeyException | InvalidParameterSpecException e) {
			// 密钥或者初始向量不匹配
		}
		return "{}";
	}

}
