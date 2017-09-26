package com.huangjw.javax.mail.model;

import java.io.Serializable;

/**
 * 邮件头中收、发人的描述模型
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class EMailAccount implements Serializable{

	private static final long serialVersionUID = 2477230739181536602L;

	/**
	 * 备注名称
	 */
	private String nickName;
	
	/**
	 * 用户邮箱地址
	 */
	private String userName;
	
	public EMailAccount() {
		
	}
	
	public EMailAccount(String name, String address) {
		this.nickName = name;
		this.userName = address;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		//return "{nickname:"+this.nickName+" , userName: "+this.userName+"}";
		return super.toString();
	}
}
