package com.volume1.ch14.threads.multi;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用{@link ReentrantLock}完成同步操作
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class SynchBankTest {

	public static final int NACCOUNTS = 100;
	public static final double INITIAL_BALANCE = 1000d;

	public static void main(String[] args) {
		Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
		for (int i = 0; i < NACCOUNTS; i++) {
			TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE);
			new Thread(r).start();
		}
	}

	/**
	 * A bank with a number of bank accounts.
	 */
	private static class Bank {
		private double[] accounts;

		/**
		 * 重入锁
		 */
		private Lock bankLock;

		/**
		 * 同步条件
		 */
		private Condition sufficientFunds;

		public Bank(int n, double initialBalance) {
			accounts = new double[n];
			for (int i = 0; i < n; i++) {
				accounts[i] = initialBalance;
			}
			bankLock = new ReentrantLock();
			sufficientFunds = bankLock.newCondition();
		}

		/**
		 * 使用独立锁机制的实现转账操作。<br/>
		 * 等价于用同步锁 {@link #transfer2(int, int, double)}实现的同步机制
		 * 
		 * @param from
		 * @param to
		 * @param amount
		 */
		public void transfer(int from, int to, double amount) {
			bankLock.lock();
			try {
				while (accounts[from] < amount) {
					// 等待直到有其他账户给当前账户转钱
					// 必须每次检查条件, 所以while替代if
					System.out.print(Thread.currentThread() + " " + from + " 金额不足，等待");
					sufficientFunds.await();
				}
				// 多线程会存在并发问题
				// 账户金额修改不是原子操作
				System.out.print(Thread.currentThread());
				accounts[from] -= amount;
				System.out.printf("%10.2f from %d to %d ", amount, from, to);
				accounts[to] = accounts[to] + amount;
				System.out.printf(" Total Balance: %10.2f \n", getTotalBalance());

				// 转账完成之后通知锁条件
				sufficientFunds.signalAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				bankLock.unlock();
			}
		}

		/**
		 * 使用同步关键字 synchronized 和对象锁机制实现的线程安全转账操作
		 * 
		 * @param from
		 * @param to
		 * @param amount
		 * @throws InterruptedException
		 */
		public synchronized void transfer2(int from, int to, double amount) throws InterruptedException {
			while (accounts[from] < amount) {
				// 等待直到有其他账户给当前账户转钱
				// 必须每次检查条件, 所以while替代if
				System.out.print(Thread.currentThread() + " " + from + " 金额不足，等待");
				wait();
			}
			// 多线程会存在并发问题
			// 账户金额修改不是原子操作
			System.out.print(Thread.currentThread());
			accounts[from] -= amount;
			System.out.printf("%10.2f from %d to %d ", amount, from, to);
			accounts[to] = accounts[to] + amount;
			System.out.printf(" Total Balance: %10.2f \n", getTotalBalance());

			// 转账完成之后通知锁条件
			notifyAll();
		}

		private double getTotalBalance() {
			bankLock.lock();
			try {
				double sum = 0;
				for (double money : accounts) {
					sum += money;
				}
				return sum;
			} finally {
				bankLock.unlock();
			}
		}

		/**
		 * Gets the number of accounts in the bank.
		 * 
		 * @return
		 */
		public int size() {
			return accounts.length;
		}
	}

	/**
	 * A runnable that transfers money from an account to another in a bank.
	 */
	private static class TransferRunnable implements Runnable {

		private Bank bank;
		private int fromAccount;
		private double maxAmount;
		private int DELAY = 1000;

		public TransferRunnable(Bank b, int from, double max) {
			this.bank = b;
			this.fromAccount = from;
			this.maxAmount = max;
		}

		@Override
		public void run() {
			long start = System.currentTimeMillis();
			long end = start;

			// 10s后退出线程
			while ((end - start) < 7000) {
				// Math.random() 返回的是[0, 1]之间的数值
				int toAccount = (int) (bank.size() * Math.random());
				double amount = maxAmount * Math.random();
				try {
					bank.transfer2(fromAccount, toAccount, amount);
					Thread.sleep((long) (DELAY * Math.random()));
				} catch (InterruptedException e) {
					break;
				}
				end = System.currentTimeMillis();
			}
		}

	}

}
