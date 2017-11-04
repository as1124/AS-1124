package com.as1124.syx.access;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;

import com.as1124.syx.service.LoginService;
import com.as1124.syx.service.WechatService;

/**
 * In common use, we register REST service via "{@link ResteasyBootstrap}" listener. 
 * In below shows another way, to create a class and extends <code>javax.ws.rs.core.Application</code>,
 * and add your REST service manually.
 * 
 * @author huangjw(as1124huang@gmail.com)
 *
 */
public class SyxRestApplication extends Application {

	private static Set<Object> singletons = new HashSet<>();

	public SyxRestApplication() {
		// default constructor
	}

	@Override
	public Set<Object> getSingletons() {
		if (singletons.isEmpty()) {
			singletons.add(new LoginService());
			singletons.add(new WechatService());
		}
		Set<Object> superSingletons = super.getSingletons();
		if (superSingletons != null && !superSingletons.isEmpty()) {
			singletons.addAll(superSingletons);
		}
		return singletons;
	}

	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> superProperties = super.getProperties();
		if (superProperties == null || superProperties.isEmpty()) {
			return null;
		} else {
			return superProperties;
		}
	}

}
