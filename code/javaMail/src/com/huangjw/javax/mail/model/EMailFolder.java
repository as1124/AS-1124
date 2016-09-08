package com.huangjw.javax.mail.model;

/**
 * 文件夹信息
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class EMailFolder {
	
	private String name;
	
	/**
	 * url
	 */
	private String urlName;
	
	/**
	 * 目录下邮件总数
	 */
	private int count;
	
	/**
	 * 未读邮件数量
	 */
	private int unReadCount;
	
	/**
	 * 新收到的邮件数
	 */
	private int newCount;
	
	private int type;
	
	public EMailFolder() {
		
	}

	/**
	 * @return the {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the {@link #name} to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the {@link #urlName}
	 */
	public String getUrlName() {
		return urlName;
	}

	/**
	 * @param urlName the {@link #urlName} to set
	 */
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	/**
	 * @return the {@link #count}
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the {@link #count} to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the {@link #unReadCount}
	 */
	public int getUnReadCount() {
		return unReadCount;
	}

	/**
	 * @param unReadCount the {@link #unReadCount} to set
	 */
	public void setUnReadCount(int unReadCount) {
		this.unReadCount = unReadCount;
	}

	/**
	 * @return the {@link #newCount}
	 */
	public int getNewCount() {
		return newCount;
	}

	/**
	 * @param newCount the {@link #newCount} to set
	 */
	public void setNewCount(int newCount) {
		this.newCount = newCount;
	}

	/**
	 * @return the {@link #type}
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the {@link #type} to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
