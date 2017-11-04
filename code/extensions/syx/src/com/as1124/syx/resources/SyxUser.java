package com.as1124.syx.resources;

import org.apache.ibatis.type.Alias;

/**
 * 数据库用户模型
 *
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
@Alias("User")
public class SyxUser {

	private int id;
	
	private String telephone;
	
	private String wechat;
	
	private String wechatName;
	
	private String childName;
	
	private String ext1;
	
	private String ext2;
	
	private String ext3;
	
	private String ext4;
	
	private String ext5;
	
	public SyxUser() {
		// default constructor
	}

	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Returns the telephone.
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telphone The telephone to set.
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return Returns the wechat.
	 */
	public String getWechat() {
		return wechat;
	}

	/**
	 * @param wechat The wechat to set.
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	/**
	 * @return Returns the wechatName.
	 */
	public String getWechatName() {
		return wechatName;
	}

	/**
	 * @param wechatName The wechatName to set.
	 */
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	/**
	 * @return Returns the childName.
	 */
	public String getChildName() {
		return childName;
	}

	/**
	 * @param childName The childName to set.
	 */
	public void setChildName(String childName) {
		this.childName = childName;
	}

	/**
	 * @return Returns the ext1.
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * @param ext1 The ext1 to set.
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * @return Returns the ext2.
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * @param ext2 The ext2 to set.
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * @return Returns the ext3.
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * @param ext3 The ext3 to set.
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	/**
	 * @return Returns the ext4.
	 */
	public String getExt4() {
		return ext4;
	}

	/**
	 * @param ext4 The ext4 to set.
	 */
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	/**
	 * @return Returns the ext5.
	 */
	public String getExt5() {
		return ext5;
	}

	/**
	 * @param ext5 The ext5 to set.
	 */
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
	
}

