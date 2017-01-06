package com.primeton.mobile.thirdparty.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.primeton.mobile.thirdparty.wechat.IWechatModel;

/**
 * 微信客服模型
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@JSONType
public class HotlineAccount implements IWechatModel{

	/**
	 * 完整客服账号，格式为：账号前缀@公众号微信号
	 */
	@JSONField
	String kf_account;
	
	/**
	 * 客服昵称
	 */
	@JSONField
	String kf_nick;
	
	/**
	 * 客服工号
	 */
	@JSONField
	String kf_id;
	
	/**
	 * 客服昵称
	 */
	@JSONField
	String nickname;
	
	/**
	 * 客服账号登录密码，格式为密码明文的32位加密MD5值。
	 * 该密码仅用于在公众平台官网的多客服功能中使用，若不使用多客服功能，
	 * 则不必设置密码
	 */
	@JSONField
	String password;
	
	/**
	 * 头像url
	 */
	@JSONField
	String kf_headimgurl;
	
	public HotlineAccount() {
	}

	public String getKf_account() {
		return kf_account;
	}

	public void setKf_account(String kf_account) {
		this.kf_account = kf_account;
	}

	public String getKf_nick() {
		return kf_nick;
	}

	public void setKf_nick(String kf_nick) {
		this.kf_nick = kf_nick;
	}

	public String getKf_id() {
		return kf_id;
	}

	public void setKf_id(String kf_id) {
		this.kf_id = kf_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKf_headimgurl() {
		return kf_headimgurl;
	}

	public void setKf_headimgurl(String kf_headimgurl) {
		this.kf_headimgurl = kf_headimgurl;
	}
	
}
