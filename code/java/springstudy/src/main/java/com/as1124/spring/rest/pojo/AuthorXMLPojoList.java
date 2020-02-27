package com.as1124.spring.rest.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "authors")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthorXMLPojoList<T> {

	@XmlElements({ @XmlElement(name = "author", type = AuthorXMLPojo.class) })
	private List<T> pojoList;

	public AuthorXMLPojoList() {
	}

	public AuthorXMLPojoList(List<T> list) {
		this.pojoList = list;
	}

	public List<T> getPojoList() {
		return pojoList;
	}

	public void setPojoList(List<T> pojoList) {
		this.pojoList = pojoList;
	}

}
