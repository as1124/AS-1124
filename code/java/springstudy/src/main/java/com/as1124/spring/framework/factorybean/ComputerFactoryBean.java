package com.as1124.spring.framework.factorybean;

import org.springframework.beans.factory.FactoryBean;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class ComputerFactoryBean implements FactoryBean<ComputerBean> {

	@Override
	public ComputerBean getObject() throws Exception {
		return new ComputerBean();
	}

	@Override
	public Class<?> getObjectType() {
		// 只影响通过 BeanFactory.getBean(Class<?> type)获取bean，如果通过
		// BeanFactory.getBean(String name)获取bean，此处返回null不影响
		return ComputerBean.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
