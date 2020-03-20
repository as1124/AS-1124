package com.as1124.spring.boot.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * OneToMany 一对多关联映射的三种方式在{@link OneToMany API文档}里面描述的很清楚共有三种方式 ；
 * <br/> 不能 transient 关联属性，否则映射关系对象查出来无数据
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Entity(name = "Author")
@Table(name = "author")
public class Author implements UserDetails {

	private static final long serialVersionUID = -587340985358125700L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer authorId;

	@Column(name = "user_name", nullable = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "address")
	private String address;

	// 方式一：mappedBy 指定的是 N 端的属性名称
	// ATTENTION 不能 transient 这个属性，否则映射关系查出来无数据
	// @OneToMany(targetEntity = Book.class, mappedBy = "author")

	// 方式二：只用在一端添加就好
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "author_id")
	private List<Book> books;

	public Author() {
		// default constructor
	}

	public Author(Integer id) {
		this.authorId = id;
	}

	public Author(String name, String pwd) {
		this.username = name;
		this.password = pwd;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return String.format("Author(%d) name is %s, address is %s", this.authorId, this.username, this.address);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("EDIT"));
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// 账号是否不过期
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 账号是否没有被锁
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 账号对应的认证密码是否没过期
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 当前账号状态是否正常
		return true;
	}

}
