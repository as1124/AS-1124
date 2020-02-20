package com.as1124.spring.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.junit.Assert;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.stereotype.Repository;

import com.as1124.spring.rmi.IUserRMIService;
import com.as1124.spring.rmi.UserRMIServiceImpl;

/**
 * 通过Spring 绑定并导出RMI服务
 * <br/>
 * ---1.创建ServiceExporter 并绑定服务，底层也是通过{@link UnicastRemoteObject#exportObject(java.rmi.Remote, int)}实现的
 * <br/>
 * ---2.装配RMI服务到Spring-Context中，以便依赖注入是可以调用
 * <br/><br/>
 * <ul>
 * <b>问题</b>
 * <li>ATTENTION 为什么在指定了host情况下总是出现 ConnectException: Connection Refused 异常 ==> 系统host映射问题???
 * </ul>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
public class SpringRMIServiceProxy {

	public static void main(String[] args) {
		try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringRMIServiceProxy.class.getPackage().getName())) {
			Object bean = ctx.getBean("rmiExporter");
			Assert.assertNotNull(bean);
			RmiServiceExporter serviceExporter = (RmiServiceExporter) bean;
			System.out.println("RMI Server is waitting for client call--->");
			try {
				serviceExporter.destroy();
			} catch (RemoteException e) {
				System.out.println("解绑 Spring RMI 服务异常");
			}
		}
	}

	/**
	 * Bind and Export RMI Service to Registry. 默认使用1099端口查找RMI注册表，如果没有发现则会创建注册表
	 * @return
	 */
	@Bean("rmiExporter")
	public RmiServiceExporter createSpringRmiExporter() {
		RmiServiceExporter rmiExporter = new RmiServiceExporter();

		//		rmiExporter.setRegistryHost("localhost");

		rmiExporter.setRegistryPort(9090);
		rmiExporter.setServicePort(9099);
		rmiExporter.setServiceName("rmiUserService");
		rmiExporter.setServiceInterface(IUserRMIService.class);
		rmiExporter.setService(new UserRMIServiceImpl());

		return rmiExporter;
	}
}
