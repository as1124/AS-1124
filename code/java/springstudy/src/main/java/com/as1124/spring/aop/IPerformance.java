package com.as1124.spring.aop;

/**
 * Spring AOP 示例中的切点模型，point-cut; 定义了三个方法作为切点
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IPerformance {

	default void broadcastShowName() {
		System.out.println("-------------broadcastShowName()---------------");
		System.out.println("节目开场白");
		System.out.println("-------------broadcastShowName()---------------");
	}

	default void broadcastShowName(String name) {
		System.out.println("-------------broadcastShowName(String)---------------");
		System.out.println("请欣赏节目 = " + name);
		System.out.println("-------------broadcastShowName(String)---------------");
	}

	void perform();
}
