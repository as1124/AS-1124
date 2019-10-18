package com.as1124.server.wxsapp.resources;

import org.apache.ibatis.type.Alias;

/**
 * 用户收件地址
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Alias("ExpressAddress")
public class UserExpressAddress {

	private int addressid = -1;

	private String openid;

	private String address;

	/**
	 * 收获地址别名、备注
	 */
	private String remark;

	/**
	 * 收件人
	 */
	private String userName;

	/**
	 * 收件人电话
	 */
	private String telphone;

	private String ext1, ext2, ext3;

	public UserExpressAddress() {
		// default constructor
	}

	/**
	 * @return Returns the {@link addressid}.
	 */
	public int getAddressid() {
		return addressid;
	}

	/**
	 * @param addressid The {@link addressid} to set.
	 */
	public void setAddressid(int addressid) {
		this.addressid = addressid;
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
	 * @return Returns the {@link address}.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address The {@link address} to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the {@link remark}.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark The {@link remark} to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return Returns the {@link userName}.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName The {@link userName} to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the {@link telphone}.
	 */
	public String getTelphone() {
		return telphone;
	}

	/**
	 * @param telphone The {@link telphone} to set.
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
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

}
