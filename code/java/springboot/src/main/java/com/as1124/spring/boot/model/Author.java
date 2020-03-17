package com.as1124.spring.boot.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
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
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "address")
	private String address;

	@OneToMany(targetEntity = Book.class)
	private List<Book> books;

	public Author() {
		// default constructor
	}

	public Author(Integer id) {
		this.authorId = id;
	}

	public Author(String name, String address) {
		this.userName = name;
		this.address = address;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public void setUserName(String name) {
		this.userName = name;
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
		return String.format("Author(%d) name is %s, address is %s", this.authorId, this.userName, this.address);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("EDIT"));
	}

	@Override
	public String getUsername() {
		return this.userName;
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
