package com.volume2.ch1.io.object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;
import com.volume2.ch1.io.Employee;

/**
 * 
 * 不直接使用序列化接口,<code>Cloneable</code>借口直接将对象写入到
 * 输出流中, 比显式的构建对象并复制或克隆数据域的克隆方法慢得多。<br>
 * A class whose clone method use serialization.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class SerialCloneable extends Employee implements Serializable, Cloneable {

	private static final long serialVersionUID = 5045921918522332491L;

	public SerialCloneable() {
		super();
	}

	public SerialCloneable(String name, double salary, int year, int month, int day) {
		super(name, salary, year, month, day);
	}

	/**
	 * 修改方法的访问修饰符为public
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		try {
			//HUANG 可以直接进行clone, 为什么还要自己写对象流进行处理?
			Object obj = super.clone();
			JavaCoreLogger.log(Level.INFO, obj.toString());

			//save the object to a byte array
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(this);

			// read a clone of the object from the byte array
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream oin = new ObjectInputStream(in);
			Object ret = oin.readObject();

			oos.close();
			out.close();
			oin.close();
			in.close();

			return ret;
		} catch (IOException | ClassNotFoundException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}
}
