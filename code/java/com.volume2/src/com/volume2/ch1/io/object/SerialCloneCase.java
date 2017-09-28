package com.volume2.ch1.io.object;

import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

/**
 * <code>Object#clone</code>方法以及
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class SerialCloneCase {

	public static void main(String[] args) {
		SerialCloneable harry = new SerialCloneable("Harry Hacker", 35000, 1989, 10, 1);
		//clone harry
		SerialCloneable harry2 = null;
		try {
			harry2 = (SerialCloneable) harry.clone();
		} catch (CloneNotSupportedException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}

		//mutate harry
		harry.raiseSalary(10);

		//now harry and clone are different
		JavaCoreLogger.log(Level.INFO, harry.toString());
		if (harry2 != null)
			JavaCoreLogger.log(Level.INFO, harry2.toString());
	}

}
