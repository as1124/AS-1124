package com.volume2.ch11.rmi;

import java.io.Serializable;

/**
 * 测试 RMI 对象参数序列化.
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 2266110925704329223L;

	private String description;

	private double price;

	private IWarehouse location;

	public Product(String description, double price) {
		this.description = description;
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public IWarehouse getLocation() {
		return location;
	}

	public void setLocation(IWarehouse location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return super.toString() + "->> desc=" + this.getDescription();
	}
}
