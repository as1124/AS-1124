package com.mobile.thirdparty.wechat.model.menu;

/**
 * 微信个性化菜单匹配规则
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatMenuRuler {

	/**
	 * 用户标签id
	 */
	String tag_id;
	
	/**
	 * 性别：1代表男，2代表女
	 */
	String sex;
	
	/**
	 * 国家
	 */
	String country;
	
	/**
	 * 省份
	 */
	String province;
	
	/**
	 * 城市
	 */
	String city;
	
	/**
	 * 客户端系统类型
	 * <li>1-iOS
	 * <li>2-Android
	 * <li>3-others
	 */
	String client_platform_type;
	
	/**
	 * 语言，如zh_CN
	 */
	String language;

	/**
	 * @return the {@link #tag_id}
	 */
	public String getTag_id() {
		return tag_id;
	}

	/**
	 * @param tag_id the {@link #tag_id} to set
	 */
	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	/**
	 * @return the {@link #sex}
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the {@link #sex} to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the {@link #country}
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the {@link #country} to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the {@link #province}
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the {@link #province} to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the {@link #city}
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the {@link #city} to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the {@link #client_platform_type}
	 */
	public String getClient_platform_type() {
		return client_platform_type;
	}

	/**
	 * @param client_platform_type the {@link #client_platform_type} to set
	 */
	public void setClient_platform_type(String client_platform_type) {
		this.client_platform_type = client_platform_type;
	}

	/**
	 * @return the {@link #language}
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the {@link #language} to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
}
