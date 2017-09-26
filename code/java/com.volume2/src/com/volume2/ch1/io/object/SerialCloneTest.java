package com.volume2.ch1.io.object;

/**
 * <code>Object#clone</code>方法以及
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class SerialCloneTest {


	public static void main(String[] args) {
		SerialCloneable harry = new SerialCloneable("Harry Hacker", 35000, 1989, 10, 1);
		//clone harry
		SerialCloneable harry2 = null;
		try {
			harry2 = (SerialCloneable)harry.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		//mutate harry
		harry.raiseSalary(10);
		
		//now harry and clone are different
		System.out.println(harry);
		System.out.println(harry2);
	}

}
