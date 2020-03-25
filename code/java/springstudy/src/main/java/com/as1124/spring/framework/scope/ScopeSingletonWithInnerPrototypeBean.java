package com.as1124.spring.framework.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Self is singleton cope, but has prototype scope field. If you use constructor wires,
 * prototype scope filed will never change because the host bean is singleton scope; so 
 * you should using setter weaving
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class ScopeSingletonWithInnerPrototypeBean {

	private ScopePrototypeBean prototypeBean;

	public ScopePrototypeBean getPrototypeBean() {
		return prototypeBean;
	}

	@Autowired
	public void setPrototypeBean(ScopePrototypeBean prototypeBean) {
		this.prototypeBean = prototypeBean;
	}
	
	
}
