package com.as1124.server.wxsapp.access;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;

import com.as1124.server.wxsapp.service.ClientService;
import com.as1124.server.wxsapp.service.GoodsInfoService;
import com.as1124.server.wxsapp.service.SurveyService;
import com.as1124.server.wxsapp.service.SurveyUserService;
import com.as1124.server.wxsapp.service.UserService;
import com.as1124.server.wxsapp.service.WechatService;

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

	@Override
	public Set<Object> getSingletons() {
		if (singletons.isEmpty()) {
			singletons.add(new UserService());
			singletons.add(new ClientService());
			singletons.add(new GoodsInfoService());
			singletons.add(new WechatService());
			singletons.add(new SurveyService());
			singletons.add(new SurveyUserService());
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
