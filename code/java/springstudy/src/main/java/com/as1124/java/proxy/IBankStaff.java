package com.as1124.java.proxy;

/**
 * A interface to describe Bank-Staff.
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IBankStaff {

	public default String getName() {
		return "秦广王的会计";
	}
}
