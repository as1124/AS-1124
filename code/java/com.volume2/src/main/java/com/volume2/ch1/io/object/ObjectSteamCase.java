package com.volume2.ch1.io.object;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamConstants;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;
import com.volume2.ch1.io.Employee;
import com.volume2.ch1.io.Manager;

/**
 * 对象序列化
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ObjectSteamCase {

	public static void main(String[] args) {
		Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);

		Manager carl = new Manager("Carl Cracker", 75000, 1987, 12, 15);
		carl.setSecretary(harry);

		Manager tony = new Manager("Tony Tester", 40000, 1990, 3, 15);
		tony.setSecretary(harry);

		Employee[] staff = new Employee[] { harry, carl, tony };
		try (FileOutputStream fileOut = new FileOutputStream("employee.dat");
				FileInputStream fileIn = new FileInputStream("employee.dat")) {
			//save all employee records to the file employee.dat
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			//居然能直接传递数组
			out.writeObject(staff);
			out.close();

			//retrieve all records into a new array
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Employee[] newStaff = (Employee[]) in.readObject();
			in.close();

			//raise secretary's salary
			newStaff[1].raiseSalary(10);

			//print the newly read employee records
			for (Employee e : newStaff) {
				JavaCoreLogger.log(Level.INFO, e.toString());
			}

			testFormat();
		} catch (IOException | ClassNotFoundException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * 测试对象序列化存储的格式
	 * 
	 * @param args
	 */
	public static void testFormat() {
		//对象序列化的头描述信息, 一个short整数
		byte data = (byte) (ObjectStreamConstants.STREAM_MAGIC & 0x00ff);
		byte data2 = (byte) ((ObjectStreamConstants.STREAM_MAGIC >> 8) & 0x00ff);

		try (FileInputStream in = new FileInputStream("employee.dat");) {
			byte[] buff = new byte[in.available()];
			in.read(buff);
			JavaCoreLogger.log(Level.INFO, (buff[0] == data2) + "|" + (buff[1] == data));
		} catch (Exception e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
