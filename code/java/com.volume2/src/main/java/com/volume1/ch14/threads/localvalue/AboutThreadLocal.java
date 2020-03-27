package com.volume1.ch14.threads.localvalue;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程同步{@link ThreadLocal}的使用<br/>
 * 所以ThreadLocal存在的目的在于解决多线程并发状态下对成员变量的操作互不影响问题；根源在于既希望保持
 * 成员变量的状态值（局部变量执行完成后值域被回收了）又希望于保持数据线程独立性
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class AboutThreadLocal {

	/**
	 * <ul>
	 * <li>如果一个变量是成员变量，那么多线程对同一个对象的成员变量进行操作时 改成员变量的操作彼此相互影响；即：一个线程对一个成员变量的操作会影响到
	 * 另一个线程
	 * <li>如果一个变量是局部变量，那么每个线程都会有一个局部变量的拷贝
	 * <strong>(即使是同一个对象中方法的变量，也会对每个线程有一个copy)</strong> 一个线程对该局部变量的改变不影响其他线程
	 * <li><code>ThreadLocal</code>
	 * </ul>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable r1 = new MySharedRunnable();
		Thread t1 = new Thread(r1, "tt1");
		Thread t2 = new Thread(r1, "tt2");
		t1.start();
		t2.start();

		Runnable r2 = new MyThreadLocal();
		Thread tt21 = new Thread(r2, "local-thread1");
		Thread tt22 = new Thread(r2, "local-thread2");
		tt21.start();
		tt22.start();
	}

	static class MySharedRunnable implements Runnable {

		/**
		 * i是成员变量，多线程并发时操作会对其他线程产生影响
		 */
		private int i = 0;

		@Override
		public void run() {
			// j是局部变量，多线程并发时，操作彼此独立不会对其他线程产生影响
			int j = 0;

			while (true) {
				System.out.println(Thread.currentThread().getName() + ": i == " + (i++) + ", j == " + (j++));
				try {
					Thread.sleep((long) Math.random() * 1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				if (i >= 10 && j >= 10) {
					break;
				}
			}
		}

	}

	static class MyThreadLocal implements Runnable {

		/**
		 * 阻止变量在多线程并发时被共享
		 */
		private ThreadLocal<AtomicInteger> localID = new ThreadLocal<AtomicInteger>() {

			@Override
			protected AtomicInteger initialValue() {
				return new AtomicInteger(0);
			}

		};

		@Override
		public void run() {
			while (true) {
				System.out.println(Thread.currentThread().getName() + ": tID == " + localID.get().getAndIncrement());
				try {
					Thread.sleep((long) Math.random() * 1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

				if (localID.get().get() >= 10) {
					break;
				}
			}
			localID.remove();
		}
	}
}
