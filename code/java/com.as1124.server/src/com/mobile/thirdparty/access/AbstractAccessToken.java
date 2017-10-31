package com.mobile.thirdparty.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;

/**
 * 
 * 基于OAuth2.0 客户端授权模型形成的JAVA模型.<BR/>
 * 第三方应用在实现过程中对于Token的描述可能稍有不同
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public abstract class AbstractAccessToken implements IAccessToken {

	/**
	 * 字段列表
	 */
	@SuppressWarnings("rawtypes")
	protected Map<?, ?> attributes = new HashMap();

	/**
	 * 客户端ID，<code>access_token</code> 的拥有者标识（像微信公众号的APPID）
	 */
	protected String clientID = "";

	/**
	 * 时间戳，记录凭证的创建时间
	 */
	protected long createTime = 0;

	/**
	 * 客户端当前状态
	 */
	protected String state = "";

	/**
	 * 表示权限范围
	 */
	protected String scope = "";

	/**
	 * 获取的凭证码
	 */
	protected String accessToken = "";

	/**
	 * 令牌类型
	 */
	protected String tokenType = "";

	/**
	 * 有效时间，单位：秒
	 */
	protected long expireIn = 0;

	/**
	 * 更新令牌
	 */
	protected String refreshToken = "";

	public AbstractAccessToken() {
		this.createTime = System.currentTimeMillis();
	}

	public Map<?, ?> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<?, ?> attributes) {
		this.attributes = attributes;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public long getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(long expireIn) {
		this.expireIn = expireIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * 初始化token相关字段信息
	 * 
	 * @param parameters 初始化token所需的一些参数
	 * @throws Exception
	 */
	protected abstract void initFields(List<NameValuePair> parameters) throws ThirdPartyRequestExceprion;
}
