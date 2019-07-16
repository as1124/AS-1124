package com.volume2.ch11.rmi;

import java.io.Serializable;

/**
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class Product implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 2266110925704329223L;

	private String description;

	private double price;

	private Warehouse location;

	public Product(String description, double price) {
		this.description = description;
		this.price = price;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the price.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return Returns the location.
	 */
	public Warehouse getLocation() {
		return location;
	}

	/**
	 * @param location The location to set.
	 */
	public void setLocation(Warehouse location) {
		this.location = location;
	}

}
