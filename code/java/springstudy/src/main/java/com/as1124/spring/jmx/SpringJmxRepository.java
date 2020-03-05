package com.as1124.spring.jmx;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;

import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.assembler.InterfaceBasedMBeanInfoAssembler;
import org.springframework.jmx.export.assembler.MBeanInfoAssembler;
import org.springframework.jmx.export.assembler.MethodExclusionMBeanInfoAssembler;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.stereotype.Repository;

import com.as1124.spring.web.model.IUserAction;
import com.as1124.spring.web.model.UserInfo;

/**
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
public class SpringJmxRepository {

	@Bean
	public UserActionImplMBean createMBean() {
		return new UserActionImplMBean();
	}

	/**
	 * 创建MBeanServer
	 * @return
	 */
	@Bean
	public MBeanServerFactoryBean createMBeanServer() {
		MBeanServerFactoryBean factoryBean = new MBeanServerFactoryBean();
		factoryBean.setLocateExistingServerIfPossible(true);
		// 不设置则使用默认值：DefaultDomain
		factoryBean.setDefaultDomain("SpringMbean");
		return factoryBean;
	}

	/**
	 * 通过Spring Exporter将普通Bean导出为MBean
	 * @param userAction
	 * @param mbeanServer
	 * @param assembler
	 * @return
	 */
	@Bean
	public MBeanExporter createMBeanExporter(UserActionImplMBean userAction, MBeanServer mbeanServer,
			MBeanInfoAssembler assembler) {
		Map<String, Object> beans = new HashMap<>();
		beans.put("SpringMbean:name=mbean-user-action", userAction);
		beans.put("SpringMbean:name=mbean-user,type=User", new UserInfo("Spring Mbean", "显示通过Spring导出MBean"));

		MBeanExporter exporter = new MBeanExporter();
		// 设置立即加载
		exporter.setAllowEagerInit(true);
		// 设置需要导出为MBean的对象
		exporter.setBeans(beans);
		exporter.setAssembler(assembler);
		exporter.setServer(mbeanServer);

		return exporter;
	}

	/**
	 * 创建MBean托管属性、方法的过滤器
	 * @return
	 */
	//	@Bean
	public MBeanInfoAssembler createMBeanInfoFilter() {
		MethodExclusionMBeanInfoAssembler assembler = new MethodExclusionMBeanInfoAssembler();
		assembler.setExposeClassDescriptor(true);
		// 不托管 toString、equals方法
		assembler.setIgnoredMethods("toString", "equals");
		return assembler;
	}

	@Bean
	public MBeanInfoAssembler createMBeanInfoFilter2() {
		InterfaceBasedMBeanInfoAssembler assembler = new InterfaceBasedMBeanInfoAssembler();
		assembler.setExposeClassDescriptor(true);
		assembler.setManagedInterfaces(IUserAction.class);
		return assembler;
	}

}
