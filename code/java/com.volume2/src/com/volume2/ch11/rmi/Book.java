package com.volume2.ch11.rmi;

/**
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public class Book extends Product {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 2364048668400821643L;

	private String author;

	public Book(String title, Double price) {
		super(title, price);
		this.setAuthor("SomeOne");
	}

	/**
	 * @return Returns the author.
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author The author to set.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

}
