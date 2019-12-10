package com.as1124.api.model.user;

import org.apache.commons.lang3.StringUtils;

import com.as1124.api.wechat.model.IWechatModel;

/**
 * 微信用户模型.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class WechatUserInfo implements IWechatModel {

	/**
	 * 是否订阅该公众号，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 */
	private int subscribe = 0;

	/**
	 * 用户微信号的对外标识，对当前公众号唯一
	 */
	private String openid = "";

	/**
	 * 用户昵称
	 */
	private String nickname = "";

	/**
	 * 性别
	 * <li>1 代表男性
	 * <li>2 代表女性
	 * <li>0 代表未知
	 */
	private int sex = 0;

	/**
	 * 语言
	 */
	private String language = "";

	/**
	 * 用户所在城市
	 */
	private String city = "";

	/**
	 * 省份
	 */
	private String province = "";

	/**
	 * 国家
	 */
	private String country = "";

	/**
	 * 用户头像url
	 */
	private String headimgurl = "";

	/**
	 * 用户关注时间，是一个时间戳。如果曾多次关注，则取最后的关注时间
	 */
	private String subscrible_time = "";

	/**
	 * 只有在用户将公众号绑定到微信开放平台后，才会出现该字段
	 */
	private String unionid = "";

	/**
	 * 公众号运营者对粉丝的备注
	 */
	private String remark = "";

	/**
	 * 用户所在的分组ID
	 */
	private int groupid = -1;

	/**
	 * 用户被打上的标签ID列表
	 */
	int[] tagid_list = null;

	public WechatUserInfo() {
		// default constructor
	}

	/**
	 * @see #subscribe
	 * @return
	 */
	public int getSubscribe() {
		return subscribe;
	}

	/**
	 * @see #subscribe
	 */
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	/**
	 * @see #openid
	 * @return
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @see #openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @see #nickname
	 * @return
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @see #nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 获取处理后的微信昵称，因为存在特殊字符以及表情符会导致部分数据库存储时异常
	 * 
	 * @param nickName
	 * @return
	 */
	public String getFilteredNickName() {
		if (StringUtils.isNotBlank(this.nickname)) {
			int length = nickname.length();
			StringBuilder temp = new StringBuilder();
			for (int i = 0; i < length; i++) {
				char a = nickname.charAt(i);
				int codePoint = nickname.codePointAt(i);
				if (codePoint > 33 && codePoint < 127) {
					//英文字符
					temp.append(a);
				} else if (codePoint >= 19968 && codePoint <= 40869) {
					//匹配中文字符的正则表达式： [\u4e00-\u9fa5]
					temp.append(a);
				}
			}
			return temp.toString();
		} else {
			return "";
		}

	}

	/**
	 * @see #sex
	 * @return
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * @see #sex
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	/**
	 * @see #language
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @see #language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @see #city
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @see #city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @see #province
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @see #province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @see #country
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @see #country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @see #headimgurl
	 * @return
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}

	/**
	 * @see #headimgurl
	 * @param headimgurl
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	/**
	 * @see #subscrible_time
	 * @return
	 */
	public String getSubscrible_time() {
		return subscrible_time;
	}

	/**
	 * @param subscribleTime
	 * @see #subscrible_time
	 */
	public void setSubscrible_time(String subscribleTime) {
		this.subscrible_time = subscribleTime;
	}

	/**
	 * @see #unionid
	 * @return
	 */
	public String getUnionid() {
		return unionid;
	}

	/**
	 * @see #unionid
	 * @return
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	/**
	 * @see #remark
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @see #remark
	 * @return
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @see #groupid
	 * @return
	 */
	public int getGroupid() {
		return groupid;
	}

	/**
	 * @see #groupid
	 * @return
	 */
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	/**
	 * @return the {@link #tagid_list}
	 */
	public int[] getTagid_list() {
		return tagid_list;
	}

	/**
	 * 
	 * @param tagidList the {@link #tagid_list} to set
	 */
	public void setTagid_list(int[] tagidList) {
		this.tagid_list = tagidList;
	}

}