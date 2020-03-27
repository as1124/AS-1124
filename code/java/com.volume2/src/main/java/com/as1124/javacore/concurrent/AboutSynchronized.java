package com.as1124.javacore.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Java并发同步问题
 * <ol>
 * <li>锁机制的实现：dup、monitorenter、monitorexit指令
 * <li>锁是否可重入? 
 * <li>锁对象是什么?
 * <li>JAVA 中锁是公平锁吗?
 * <li>乐观锁和悲观锁? synchronized是哪一种?
 * <li>JDK 对锁机制做了哪些优化? 自旋锁和互斥锁是什么?
 * <li>独立锁 {@linkplain ReentrantLock}的使用；对象锁{@linkplain Object#wait()}的使用
 * </ol>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class AboutSynchronized {

	public static void main(String[] args) {
		// 锁的是Class对象
		Thread ta = new Thread(() -> {
			AboutSynchronized.setC("Static A");
		}, "Static A");
		Thread tb = new Thread(() -> {
			AboutSynchronized.setC("Static B");
		}, "Static B");
		ta.start();
		tb.start();

		// 锁对象是forLock
		AboutSynchronized forLock = new AboutSynchronized("locker");
		Thread tc = new Thread(() -> forLock.setB("Instance C"), "Instance C");
		Thread td = new Thread(() -> forLock.setB("Instance D"), "Instance D");
		tc.start();
		td.start();

		// te和tf不存在锁竞争
		AboutSynchronized nonLockA = new AboutSynchronized("nonLockeA");
		AboutSynchronized nonLockB = new AboutSynchronized("nonLockeB");
		Thread te = new Thread(() -> nonLockA.setB("nonLockeA"));
		Thread tf = new Thread(() -> nonLockB.setB("nonLockeB"));
		te.start();
		tf.start();
	}

	private String a;

	private volatile String b;

	private static String c;

	private static volatile String d;

	public AboutSynchronized(String name) {
		this.a = name;
	}

	public String getA() {
		return a;
	}

	public String getB() {
		synchronized (this) {
			return b;
		}
	}

	public static synchronized String getC() {
		return c;
	}

	public static String getD() {
		synchronized (AboutSynchronized.class) {
			return d;
		}
	}

	public void setA(String aRef) {
		this.a = aRef;
	}

	public void setB(String bRef) {
		synchronized (this) {
			this.b = bRef;
		}
	}

	public static synchronized void setC(String cRef) {
		AboutSynchronized.c = cRef;
	}

	public static void setD(String dRef) {
		synchronized (AboutSynchronized.class) {
			AboutSynchronized.d = dRef;
		}
	}

}
