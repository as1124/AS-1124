package com.priemton.mobile.thirdparty.access;

import java.util.HashMap;
import java.util.Map;

import com.primeton.mobile.wechat.model.IWechatModel;

/**
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public abstract class AbstractAccessToken implements IAccessToken, IWechatModel{
	
	protected Map<?, ?> attributes = new HashMap();
	
	/**
	 * <code>access_token</code> 的拥有者标识（像微信公众号的APPID）
	 */
	protected String ownerID = null;
	
	/**
	 * 时间戳，记录凭证的创建时间
	 */
	protected long createTime = 0;
	
	/**
	 * @return the {@link #ownerID}
	 */
	public String getOwnerID() {
		return ownerID;
	}

	/**
	 * @param ownerID the {@link #ownerID} to set
	 */
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
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
	 * 初始化token相关字段信息
	 * 
	 * @param parameters 初始化token所需的一些参数
	 */
	protected abstract void initFields(Map<String, String> parameters) throws Exception;
}
