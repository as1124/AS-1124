package com.volume2.ch11.rmi;

/**
 * 测试 RMI 对象参数序列化.
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return super.toString() + ": author == " + getAuthor();
	}
}
