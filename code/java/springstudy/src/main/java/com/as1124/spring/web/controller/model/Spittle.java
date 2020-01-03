package com.as1124.spring.web.controller.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * 基础的POJO测试模型
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class Spittle {

	private Long id;

	/**
	 * Comment for <code>userName</code>： 非空，以及长度校验
	 */
	@NotNull
	@Size(min = 5, max = 20)
	private String userName;

	/**
	 * Comment for <code>time</code>: 过去的某个时间
	 */
	@Null
	@Past
	private Date time;

	@NotNull
	@Size(min = 5, max = 25)
	private String password;

	public Spittle(String username, String pwd) {
		this.userName = username;
		this.password = pwd;
	}

	/**
	 * @return Returns the {@link #id}.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id The {@link #id} to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the {@link #userName}.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName The {@link #userName} to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the {@link #time}.
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time The {@link #time} to set.
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return Returns the {@link #password}.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password The {@link #password} to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Name is " + this.userName + ", pdd== " + this.password;
	}

}
