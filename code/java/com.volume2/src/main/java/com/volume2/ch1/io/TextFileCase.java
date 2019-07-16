package com.volume2.ch1.io;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

/**
 * 文本读写测试
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class TextFileCase {

	public static void main(String[] args) {
		Employee[] staff = new Employee[3];

		staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
		staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
		staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

		try {
			//save all employee records to the file employee.txt
			PrintWriter out = new PrintWriter(new FileWriter("employee.txt"));
			writeData(staff, out);
			out.close();

			//retrieve all records into a new array
			Scanner in = new Scanner(new FileInputStream("employee.txt"));
			Employee[] newStaff = readData(in);
			in.close();

			for (Employee emp : newStaff) {
				//print the newly read employee records
				JavaCoreLogger.log(Level.INFO, emp.toString());
			}
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Write all employees in an array to a print writer
	 * @param staff
	 * @param out
	 */
	public static void writeData(Employee[] staff, PrintWriter out) {
		out.println(staff.length);
		for (Employee e : staff) {
			e.writeData(out);
		}
	}

	/**
	 * Reads an array of employees from a scanner
	 * @param in
	 * @return
	 */
	public static Employee[] readData(Scanner in) {
		int size = in.nextInt();

		//读取当前行剩下的内容
		in.nextLine();

		Employee[] newStaff = new Employee[size];
		for (int i = 0; i < size; i++) {
			newStaff[i] = new Employee();
			newStaff[i].readData(in);
		}

		return newStaff;
	}
}
