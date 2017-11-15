package com.mobile.thirdparty.wechat.model.hotline;

import com.mobile.thirdparty.wechat.model.IWechatModel;

/**
 * 微信客服账号模型
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class HotlineAccount implements IWechatModel {

	/**
	 * 完整客服账号，格式为：账号前缀@公众号微信号
	 */
	private String kf_account = "";

	/**
	 * 客服昵称
	 */
	private String kf_nick = "";

	/**
	 * 客服工号
	 */
	private String kf_id = "";

	/**
	 * 头像url
	 */
	private String kf_headimgurl = "";

	/**
	 * 如果客服账号已绑定了个人微信，此字段显示客服人员微信
	 * 
	 * Comment for <code>kf_wx</code>
	 */
	private String kf_wx = "";

	/**
	 * 邀请绑定的微信号
	 * 
	 * Comment for <code>invite_wx</code>
	 */
	private String invite_wx = "";

	/**
	 * 邀请的过期时间
	 * 
	 * Comment for <code>invite_expire_time</code>
	 */
	private String invite_expire_time = "";

	/**
	 * 邀请的状态，有等待确认“waiting”，被拒绝“rejected”，
	 * 过期“expired”
	 * 
	 * Comment for <code>invite_status</code>
	 */
	private String invite_status = "";

	/**
	 * 客服昵称
	 */
	private String nickname = "";

	/**
	 * 客服账号登录密码，格式为密码明文的32位加密MD5值。
	 * 该密码仅用于在公众平台官网的多客服功能中使用，若不使用多客服功能，
	 * 则不必设置密码
	 */
	private String password = "";

	public HotlineAccount() {
		// default constructor
	}

	public HotlineAccount(String account, String nickName, String password) {
		this.kf_account = account;
		this.nickname = nickName;
		this.password = password;
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

	/**
	 * @return Returns the kf_wx.
	 */
	public String getKf_wx() {
		return kf_wx;
	}

	/**
	 * @param kf_wx The kf_wx to set.
	 */
	public void setKf_wx(String kf_wx) {
		this.kf_wx = kf_wx;
	}

	/**
	 * @return Returns the invite_wx.
	 */
	public String getInvite_wx() {
		return invite_wx;
	}

	/**
	 * @param invite_wx The invite_wx to set.
	 */
	public void setInvite_wx(String invite_wx) {
		this.invite_wx = invite_wx;
	}

	/**
	 * @return Returns the invite_expire_time.
	 */
	public String getInvite_expire_time() {
		return invite_expire_time;
	}

	/**
	 * @param invite_expire_time The invite_expire_time to set.
	 */
	public void setInvite_expire_time(String invite_expire_time) {
		this.invite_expire_time = invite_expire_time;
	}

	/**
	 * @return Returns the invite_status.
	 */
	public String getInvite_status() {
		return invite_status;
	}

	/**
	 * @param invite_status The invite_status to set.
	 */
	public void setInvite_status(String invite_status) {
		this.invite_status = invite_status;
	}

}
