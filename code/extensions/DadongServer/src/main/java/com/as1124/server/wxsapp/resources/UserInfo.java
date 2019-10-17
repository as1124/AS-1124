package com.as1124.server.wxsapp.resources;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

/**
 * 用户信息
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Alias("UserInfo")
public class UserInfo {

	private int userid;

	/**
	 * 微信账号下的用户id
	 */
	private String openid;

	/**
	 * 开放平台下用户id
	 */
	private String unionid;

	/**
	 * 当前用户所属的微信APP账号
	 */
	private String appid;

	/**
	 * 用户等级：VIP/普通：0-普通，1-VIP
	 */
	private int userLevel;

	/**
	 * 微信昵称
	 */
	private String wechatName;

	private String name;

	/**
	 * 头像URL
	 */
	private String headIcon;

	/**
	 * 手机号
	 */
	private String telephone;

	/**
	 * 账号注册时间
	 */
	private Date registerTime;

	/**
	 * 用户状态：0-注销，1-正常
	 */
	private int userStatus;

	/**
	 * 购物车信息，JSON文本
	 */
	private String goodsCar;

	private String ext1;

	private String ext2;

	private String ext3;

	private String ext4;

	private String ext5;

	/**
	 * 快递收获地址信息
	 */
	private List<HashMap<String, Object>> expressAddress;

	public UserInfo() {
		// default constructor
	}

	/**
	 * @return Returns the {@link userid}.
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * @param userid The {@link userid} to set.
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * @return Returns the {@link openid}.
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid The {@link openid} to set.
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @return Returns the {@link unionid}.
	 */
	public String getUnionid() {
		return unionid;
	}

	/**
	 * @param unionid The {@link unionid} to set.
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	/**
	 * @return Returns the {@link appid}.
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * @param appid The {@link appid} to set.
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @return Returns the {@link userLevel}.
	 */
	public int getUserLevel() {
		return userLevel;
	}

	/**
	 * @param userLevel The {@link userLevel} to set.
	 */
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	/**
	 * @return Returns the {@link wechatName}.
	 */
	public String getWechatName() {
		return wechatName;
	}

	/**
	 * @param wechatName The {@link wechatName} to set.
	 */
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	/**
	 * @return Returns the {@link name}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The {@link name} to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the {@link headIcon}.
	 */
	public String getHeadIcon() {
		return headIcon;
	}

	/**
	 * @param headIcon The {@link headIcon} to set.
	 */
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

	/**
	 * @return Returns the {@link telephone}.
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone The {@link telephone} to set.
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return Returns the {@link registerTime}.
	 */
	public Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * @param registerTime The {@link registerTime} to set.
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * @return Returns the {@link userStatus}.
	 */
	public int getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus The {@link userStatus} to set.
	 */
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * @return Returns the {@link goodsCar}.
	 */
	public String getGoodsCar() {
		return goodsCar;
	}

	/**
	 * @param goodsCar The {@link goodsCar} to set.
	 */
	public void setGoodsCar(String goodsCar) {
		this.goodsCar = goodsCar;
	}

	/**
	 * @return Returns the {@link ext1}.
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * @param ext1 The {@link ext1} to set.
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * @return Returns the {@link ext2}.
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * @param ext2 The {@link ext2} to set.
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * @return Returns the {@link ext3}.
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * @param ext3 The {@link ext3} to set.
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	/**
	 * @return Returns the {@link ext4}.
	 */
	public String getExt4() {
		return ext4;
	}

	/**
	 * @param ext4 The {@link ext4} to set.
	 */
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	/**
	 * @return Returns the {@link ext5}.
	 */
	public String getExt5() {
		return ext5;
	}

	/**
	 * @param ext5 The {@link ext5} to set.
	 */
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	/**
	 * @return Returns the {@link expressAddress}.
	 */
	public List<HashMap<String, Object>> getExpressAddress() {
		return expressAddress;
	}

	/**
	 * @param expressAddress The {@link expressAddress} to set.
	 */
	public void setExpressAddress(List<HashMap<String, Object>> expressAddress) {
		this.expressAddress = expressAddress;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof UserInfo) {
			UserInfo another = (UserInfo) obj;
			if (this.userid > 0 && another.userid > 0) {
				return this.userid == another.getUserid();
			} else if (StringUtils.isNotBlank(this.openid) && StringUtils.isNotBlank(another.getOpenid())) {
				return this.openid.equals(another.getOpenid());
			} else if (StringUtils.isNotBlank(this.unionid) && StringUtils.isNotBlank(another.getUnionid())) {
				// ATTENTION unionid一致就视为同一个用户是否妥当
				return this.unionid.equals(another.getUnionid());
			}
		}
		return false;
	}
}
