package com.volume2.ch1.io;

/**
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class Manager extends Employee {

	private Employee secretary;

	/**
	 * 自类型也需要有自己的序列号
	 * @HUANG 如果父子类型的序列号一致会不会出错??(正常)
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a Manager without a secretary
	 * @param name
	 * @param s
	 * @param year
	 * @param month
	 * @param day
	 */
	public Manager(String name, double s, int year, int month, int day) {
		super(name, s, year, month, day);
		secretary = null;
	}

	/**
	 * @return
	 */
	public Employee getSecretary() {
		return secretary;
	}

	/**
	 * Assign a secretary to the manager
	 * @param secretary
	 */
	public void setSecretary(Employee secretary) {
		this.secretary = secretary;
	}

	@Override
	public String toString() {
		return super.toString() + "[secretary=" + secretary + "]";
	}
}
