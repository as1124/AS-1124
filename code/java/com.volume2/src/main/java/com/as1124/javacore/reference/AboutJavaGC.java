package com.as1124.javacore.reference;

import java.lang.ref.SoftReference;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class AboutJavaGC {

	private static int count = 1;

	public static void main(String[] args) {
		ReferenceObjectTest obj = new ReferenceObjectTest();
		System.gc();
		SoftReference<ReferenceObjectTest> softRefer = new SoftReference<ReferenceObjectTest>(obj);
		sleepThread();

		obj = null;

		System.gc();
		sleepThread();
	}

	private static void sleepThread() {
		System.out.println("Waitting for GC: " + (count++));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Thread was interrupted!!");
			e.printStackTrace();
		}
	}
}
