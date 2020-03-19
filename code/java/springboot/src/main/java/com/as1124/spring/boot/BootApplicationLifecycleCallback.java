package com.as1124.spring.boot;

import javax.annotation.PreDestroy;

/**
 * lifecycle listener
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class BootApplicationLifecycleCallback {

	@PreDestroy
	public void preDestoryApplication() {
		System.out.println("[As1124]----------即将销毁 Spring Boot Application -------------");
	}
}
