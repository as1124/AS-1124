package com.volume2.ch11.rmi;

/**
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class Book extends Product {

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
