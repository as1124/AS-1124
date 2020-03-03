package com.as1124.spring.aop;

public class PerformanceImpl implements IPerformance {

	@Override
	public void perform() {
		System.out.println("-------------performance()---------------");
		System.out.println("【美食从天而降】");
		System.out.println("-------------performance()---------------");
	}

}
