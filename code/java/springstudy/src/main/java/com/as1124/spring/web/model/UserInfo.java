package com.as1124.spring.web.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * 基础的POJO测试模型, {@link Entity} 是JPA持久化模型注解
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Entity
public class UserInfo {

	@Min(0)
	private int id;

	@NotNull
	@Size(min = 5, max = 20)
	private String userName;

	@Null
	@Past
	private Date birthday;

	@Size(min = 10, max = 50)
	private String address;

	public UserInfo(int uid, String username) {
		this.id = uid;
		this.userName = username;
	}

	public UserInfo(String username, String address) {
		this.userName = username;
		this.address = address;
	}

	/**
	 * @return Returns the {@link #id}.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id The {@link #id} to set.
	 */
	public void setId(int id) {
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
	 * @return Returns the {@link #birthday}.
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param time The {@link #birthday} to set.
	 */
	public void setBirthday(Date birth) {
		this.birthday = birth;
	}

	/**
	 * @return Returns the {@link #address}.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address The {@link #address} to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Name is " + this.userName + ", addr== " + this.address;
	}

}
