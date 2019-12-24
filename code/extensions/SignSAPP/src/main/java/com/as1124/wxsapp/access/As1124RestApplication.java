package com.as1124.wxsapp.access;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;

import com.as1124.wxsapp.sign.service.ActivityService;

/**
 * In common use, we register REST service via "{@link ResteasyBootstrap}"
 * listener. In below shows another way, to create a class and extends
 * <code>javax.ws.rs.core.Application</code>, and add your REST service
 * manually.
 * 
 * @author As-1124(as1124huang@gmail.com)
 *
 */
public class As1124RestApplication extends Application {

	private static Set<Object> singletons = new HashSet<>();

	public As1124RestApplication() {
		// default constructor
	}

	/**
	 * 这里提供的REST处理类是不做单例处理
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> serviceClazz = new HashSet<>();

		serviceClazz.add(ActivityService.class);
		serviceClazz.add(ServerSettingService.class);

		return serviceClazz;
	}

	/** 
	 * 这里提供的REST处理类都会被作为单例处理
	 * @see javax.ws.rs.core.Application#getSingletons()
	 */
	@Override
	public Set<Object> getSingletons() {
		if (singletons.isEmpty()) {
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
