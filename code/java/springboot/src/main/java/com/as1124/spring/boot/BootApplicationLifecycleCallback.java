package com.as1124.spring.boot;

import javax.annotation.PreDestroy;

/**
 * TODO 此处填写 class 信息
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class BootApplicationLifecycleCallback {

	@PreDestroy
	public void preDestoryApplication() {
		System.out.println("[As1124]----------即将销毁 Spring Boot Application -------------");
	}
}
