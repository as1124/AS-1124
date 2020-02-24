package com.as1124.spring.rest;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 通过 JAXB 框架实现 xml 和 java 对象相互序列话
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthorXMLPojo implements Serializable {

	private static final long serialVersionUID = -6530029766376518332L;

	@XmlAttribute
	private String authorName;

	@XmlAttribute
	private int age;

	@XmlElement
	private String desc;

	@XmlElementWrapper(name = "bookList")
	@XmlElement(name = "book")
	private List<BookXMLPojo> books;

	public AuthorXMLPojo() {
		// default constructor
	}

	public AuthorXMLPojo(String name, String desc) {
		this.authorName = name;
		this.desc = desc;
	}

	public String getAuthorName() {
		return this.authorName;
	}

	public void setAuthorName(String name) {
		this.authorName = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<BookXMLPojo> getBooks() {
		return books;
	}

	public void setBooks(List<BookXMLPojo> books) {
		this.books = books;
	}

}
