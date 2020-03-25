package com.as1124.spring.framework.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class ScopePrototypeBean {

	public final int hashValue;

	public ScopePrototypeBean() {
		hashValue = hashCode();
	}
}
