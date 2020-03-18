package com.as1124.spring.boot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <b>演示Bean属性外置，即从配置文件(properties、yml)中通过set方法自动注入</b>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Component
@ConfigurationProperties(prefix = "as1124.bookstore")
public class As1124BootProperties {

	/**
	 * 书店名称
	 */
	private String storeName;

	/**
	 * Comment for <code>has</code>：是否有存货
	 */
	private boolean has;

	/**
	 * 书店评分
	 */
	private int rate;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public boolean isHas() {
		return has;
	}

	public void setHas(boolean has) {
		this.has = has;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
