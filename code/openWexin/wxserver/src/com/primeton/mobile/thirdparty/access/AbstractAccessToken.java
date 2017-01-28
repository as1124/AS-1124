package com.primeton.mobile.thirdparty.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import com.primeton.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;

/**
 * 
 * 基于OAuth2.0 客户端授权模型形成的JAVA模型.<BR/>
 * 第三方在实现过程中对于Token的描述可能稍有不同
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
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
	protected String client_id = null;
	
	/**
	 * 时间戳，记录凭证的创建时间
	 */
	protected long createTime = 0;
	
	/**
	 * 客户端当前状态
	 */
	protected String state = null;
	
	/**
	 * 表示权限范围
	 */
	protected String scope = null;
	
	/**
	 * 获取的凭证码
	 */
	protected String access_token = "";
	
	/**
	 * 令牌类型
	 */
	protected String token_type = null;
	
	/**
	 * 有效时间，单位：秒
	 */
	protected long expires_in = 0;
	
	/**
	 * 更新令牌
	 */
	protected String refresh_token = null;
	
	public AbstractAccessToken() {
	}
	
	/**
	 * @return the {@link #attributes}
	 */
	public Map<?, ?> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the {@link #attributes} to set
	 */
	public void setAttributes(Map<?, ?> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the {@link #client_id}
	 */
	public String getClient_id() {
		return client_id;
	}

	/**
	 * @param client_id the {@link #client_id} to set
	 */
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	/**
	 * @return the {@link #createTime}
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the {@link #createTime} to set
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the {@link #state}
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the {@link #state} to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the {@link #scope}
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope the {@link #scope} to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAccess_token() {
		return access_token;
	}

	/**
	 * @param access_token the {@link #access_token} to set
	 */
	public void setAccess_token(String access_token) {
		if(access_token == null){
			this.access_token = "";
		}
		this.access_token = access_token;
	}

	/**
	 * @return the {@link #token_type}
	 */
	public String getToken_type() {
		return token_type;
	}

	/**
	 * @param token_type the {@link #token_type} to set
	 */
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	/**
	 * @return the {@link #expires_in}
	 */
	public long getExpires_in() {
		return expires_in;
	}

	/**
	 * @param expires_in the {@link #expires_in} to set
	 */
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	/**
	 * @return the {@link #refresh_token}
	 */
	public String getRefresh_token() {
		return refresh_token;
	}

	/**
	 * @param refresh_token the {@link #refresh_token} to set
	 */
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	/**
	 * 初始化token相关字段信息
	 * 
	 * @param parameters 初始化token所需的一些参数
	 * @throws Exception
	 */
	protected abstract void initFields(List<BasicNameValuePair> parameters) throws ThirdPartyRequestExceprion;
}
