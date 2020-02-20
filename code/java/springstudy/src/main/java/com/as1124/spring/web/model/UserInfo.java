package com.as1124.spring.web.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * 基础的POJO测试模型，{@link Entity} 是JPA持久化模型注解
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 2258011765463964727L;

	//	GenerationType.SEQUENCE/TABLE 都依赖于数据库，即：创建表时需要设置主键自增策略

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)

	@Min(0)
	private Integer id;

	@Column(name = "user_name")

	@NotNull
	@Size(min = 5, max = 20)
	private String userName;

	@Column(name = "birthday")

	@Null
	@Past
	private Date birthday;

	@Column(name = "address")

	@Size(min = 10, max = 50)
	private String address;

	public UserInfo() {
		// Default constructor
	}

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
	public Integer getId() {
		return id;
	}

	/**
	 * @param id The {@link #id} to set.
	 */
	public void setId(Integer id) {
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
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (UserInfo.class.isAssignableFrom(obj.getClass())) {
			UserInfo another = (UserInfo) obj;
			return ((this.id == another.getId()) && this.userName.equals(another.getUserName()));
		}
		return false;
	}

	@Override
	public String toString() {
		return "Name is " + this.userName + ", addr== " + this.address;
	}

}
