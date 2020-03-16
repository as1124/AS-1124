package com.as1124.java.proxy;

/**
 * 代理测试接口
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IBankService {

	public default String getName() {
		return "冥府银行";
	}

	public default double getBalance(String accountID) {
		System.out.println("账户余额  = 0.00");
		return 0;
	}

	public default void saveMoney(String accountId, double count) {
		System.out.println("收到储蓄款 = " + count);
	}

	/**
	 * 测试和 Object 中同名冲突的方法
	 * @return
	 */
	public String toString();
}
