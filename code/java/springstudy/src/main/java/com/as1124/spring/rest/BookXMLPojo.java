package com.as1124.spring.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookXMLPojo {

	@XmlElement
	private String name;

	@XmlElement
	private String publishDate;

	public BookXMLPojo() {
		// default constructor
	}

	public BookXMLPojo(String name, String date) {
		this.name = name;
		this.publishDate = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

}
