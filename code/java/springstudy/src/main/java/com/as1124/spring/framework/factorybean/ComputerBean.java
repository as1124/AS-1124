package com.as1124.spring.framework.factorybean;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class ComputerBean {

	private int hashValue;

	public ComputerBean() {
		this.hashValue = this.hashCode();
	}

	public int getHashValue() {
		return hashValue;
	}
}
