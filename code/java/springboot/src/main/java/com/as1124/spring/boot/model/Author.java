package com.as1124.spring.boot.model;

import java.util.List;

/**
 * TODO 此处填写 class 信息
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */

public class Author {

	private Integer authorId;

	private String name;

	private String address;

	private List<Book> books;

	public Author() {
		// default constructor
	}

	public Author(Integer id) {
		this.authorId = id;
	}

	public Author(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return String.format("Author({0}) name is {1}, address is {2}", this.authorId, this.name, this.address);
	}
}
