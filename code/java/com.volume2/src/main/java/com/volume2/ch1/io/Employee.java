package com.volume2.ch1.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * 文本输入输出
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class Employee implements Serializable {

	/**
	 * 对象序列号
	 */
	private static final long serialVersionUID = 1L;

	public static final int NAME_SIZE = 40;

	//YEAR|MONTH|DAY
	public static final int RECORD_SIZE = 2 * NAME_SIZE + 8 + 4 + 4 + 4;

	/**
	 * 最多允许存40个字符，RandomAccessFile
	 */
	private String name;

	private double salary;

	private Date hireDay;

	public Employee() {

	}

	public Employee(String name, double salary, int year, int month, int day) {
		this.name = name;
		this.salary = salary;
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		this.hireDay = calendar.getTime();
	}

	/**
	 * Writes employee data to a print writer
	 * @param out
	 */
	public void writeData(PrintWriter out) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(this.hireDay);

		out.println(this.getName() + "|" + this.salary + "|" + calendar.get(GregorianCalendar.YEAR) + "|"
				+ (calendar.get(GregorianCalendar.MONTH) + 1) + "|" + calendar.get(GregorianCalendar.DAY_OF_MONTH));
	}

	public void writeDataUseDataOutput(DataOutput out) throws IOException {
		DataIO.writeFixedString(name, NAME_SIZE, out);
		out.writeDouble(this.salary);

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(this.hireDay);
		int year = calendar.get(GregorianCalendar.YEAR);
		int month = calendar.get(GregorianCalendar.MONTH) + 1;
		int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		out.writeInt(year);
		out.writeInt(month);
		out.writeInt(day);
	}

	/**
	 * Reads employee data from a buffer reader
	 * @param in
	 */
	public void readData(Scanner in) {
		String info = in.nextLine();

		//ATTENTION 正则表达式中'|'有特殊意义??
		String[] tokens = info.split("\\|");
		this.name = tokens[0];
		this.salary = Double.parseDouble(tokens[1]);
		int year = Integer.parseInt(tokens[2]);
		int month = Integer.parseInt(tokens[3]) - 1;
		int day = Integer.parseInt(tokens[4]);
		this.hireDay = new GregorianCalendar(year, month, day).getTime();
	}

	public void readDataUserDataInput(DataInput in) throws IOException {
		this.name = DataIO.readFixedString(NAME_SIZE, in);
		this.salary = in.readDouble();
		int year = in.readInt();
		int month = in.readInt();
		int day = in.readInt();

		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		this.hireDay = calendar.getTime();
	}

	/**
	 * Raises the salary of this employee.
	 * @param byPercent
	 */
	public void raiseSalary(double byPercent) {
		this.salary = this.salary * (1 + byPercent);
	}

	/**
	 * 
	 * @see name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see salary
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalay(double salary) {
		this.salary = salary;
	}

	/**
	 * @see hireDay
	 * @return the hireDay
	 */
	public Date getHireDay() {
		return hireDay;
	}

	/**
	 * @param hireDay the hireDay to set
	 */
	public void setHireDay(Date hireDay) {
		this.hireDay = hireDay;
	}

	public String toString() {
		String result = "";
		result = getClass() + "[name=" + name + ", salary=" + salary + ", hireDay=" + hireDay + "]";
		return result;
	}

}
