package com.as1124.spring.boot.model;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class Book {

	private Integer bookID;

	private String bookName;

	private double price;

	public Book() {
		// default constructor
	}

	public Book(Integer id, String name) {
		this.bookID = id;
		this.bookName = name;
	}

	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format("Book named {0}-{1}, price is {2}", this.bookID, this.bookName, this.price);
	}
}
