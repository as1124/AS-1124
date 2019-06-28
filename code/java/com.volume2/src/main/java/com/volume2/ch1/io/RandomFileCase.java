package com.volume2.ch1.io;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

public class RandomFileCase {

	public static void main(String[] args) {
		Employee[] staff = new Employee[3];
		staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
		staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
		staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

		try {
			//save all employee records to the file
			DataOutputStream out = new DataOutputStream(new FileOutputStream("employee2.txt"));
			for (Employee e : staff) {
				e.writeDataUseDataOutput(out);
			}
			out.close();

			//retrieve all records into a new array
			RandomAccessFile in = new RandomAccessFile("employee2.txt", "rw");
			//compute the array size
			int size = (int) (in.length() / Employee.RECORD_SIZE);
			Employee[] newStaff = new Employee[size];

			//read employees in reverse order
			for (int i = size - 1; i >= 0; i--) {
				newStaff[i] = new Employee();
				//move the pointer
				in.seek(i * Employee.RECORD_SIZE);
				newStaff[i].readDataUserDataInput(in);
			}
			in.close();

			//print the newly read employee records
			for (Employee e : newStaff) {
				JavaCoreLogger.log(Level.INFO, e.toString());
			}
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
