package com.as1124.spring.boot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Entity(name = "Book")
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer bookID;

	@Column(name = "reader")
	private String reader;

	@Column(name = "isbn")
	private String isbn;

	@Column(name = "title")
	private String title;

	@Column(name = "price")
	private double price;

	@Column(name = "description")
	private String description;

	@Column(name = "author_id")
	private String authorId;

	// ATTENTION: 如果两边都设置关联查询的话在解析成JSON、XML传递到客户端就出现循环解析了，
	// 此时内存也就 StackOverFlow 了；所以这里通过 @Transient注解使其不参与持久化
	//	@ManyToOne(targetEntity = Author.class)
	//	@JoinColumn(name = "author_id")
	@Transient
	private Author author;

	public Book() {
		// default constructor
	}

	public Book(Integer id, String title) {
		this.bookID = id;
		this.title = title;
	}

	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format("Book named %d-%s, price is %d", this.bookID, this.title, this.price);
	}
}
