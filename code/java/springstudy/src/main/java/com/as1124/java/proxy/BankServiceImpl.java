package com.as1124.java.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Bank-Service default implementation
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class BankServiceImpl implements IBankService {

	private Map<String, Double> accounts = new HashMap<>();

	public BankServiceImpl() {
		accounts.put("Tom", 100.10);
		accounts.put("Jerry", 2020.02);
		accounts.put("As-1124", 124.00);
	}

	@Override
	public String getName() {
		return "getName on BankServiceImpl";
	}

	@Override
	public double getBalance(String accountID) {
		Double balance = accounts.getOrDefault(accountID, 0.0);
		System.out.println(accountID + " 余额 = " + balance);
		return balance;
	}

	@Override
	public void saveMoney(String accountID, double count) {
		double balance = accounts.getOrDefault(accountID, 0.0);
		System.out.println(accountID + " 新增储蓄 = " + count);
		accounts.put(accountID, (balance + count));
	}

	@Override
	public String toString() {
		return "This is BankServiceImpl's toString, override its parent";
	}

}
