package com.volume1.ch14.threads.multi;

/**
 * 多线程并发操作, 非同步安全情况下
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class UnsynchBankTest {

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

		public Bank(int n, double initialBalance) {
			accounts = new double[n];
			for (int i = 0; i < n; i++) {
				accounts[i] = initialBalance;
			}
		}

		/**
		 * Transfers money from one account to another.
		 * 
		 * @param from
		 * @param to
		 * @param amount
		 */
		public void transfer(int from, int to, double amount) {
			if (accounts[from] < amount) {
				return;
			}
			// 多线程会存在并发问题
			// 账户金额修改不是原子操作
			System.out.print(Thread.currentThread());
			accounts[from] -= amount;
			System.out.printf("%10.2f from %d to %d ", amount, from, to);
			accounts[to] = accounts[to] + amount;
			System.out.printf(" Total Balance: %10.2f \n", getTotalBalance());
		}

		private double getTotalBalance() {
			double sum = 0;
			for (double money : accounts) {
				sum += money;
			}
			return sum;
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
				bank.transfer(fromAccount, toAccount, amount);
				try {
					Thread.sleep((long) (DELAY * Math.random()));
				} catch (InterruptedException e) {
					break;
				}
				end = System.currentTimeMillis();
			}
		}

	}

}
