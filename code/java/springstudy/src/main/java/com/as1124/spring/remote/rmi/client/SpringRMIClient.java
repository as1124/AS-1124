package com.as1124.spring.remote.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiInvocationHandler;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Repository;

import com.as1124.spring.remote.rmi.IUserRMIService;

/**
 * Spring RMI 服务测试
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
public class SpringRMIClient {

	private static final String RMI_URL = "rmi://localhost:9090/rmiUserService";

	@Autowired
	IUserRMIService userRmiService;

	public static void main(String[] args) {
		try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringRMIClient.class.getPackage().getName())) {
			Object bean = ctx.getBean("rmiProxyFactory");
			Assert.assertNotNull(bean);
			if (IUserRMIService.class.isAssignableFrom(bean.getClass())) {
				System.out.println(((IUserRMIService) bean).getName(0));
				System.out.println(((IUserRMIService) bean).getAllUsers().get(0).getUserName());
			}
			SpringRMIClient client = ctx.getBean(SpringRMIClient.class);
			if (client != null) {
				client.testRMIByJDK();
				client.testRMIBySpring();
			}
		}
	}

	/**
	 * 方法设置为static 是为了让 SpringRMIClient中的 Autowired 的依赖优先加载
	 * 
	 * @return
	 */
	@Bean("rmiProxyFactory")
	public static RmiProxyFactoryBean prepareUserRmiService() {
		RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
		factoryBean.setServiceInterface(IUserRMIService.class);
		factoryBean.setServiceUrl(RMI_URL);
		return factoryBean;
	}

	private void testRMIByJDK() {
		try {
			Remote remoteObj = Naming.lookup(RMI_URL);
			if (IUserRMIService.class.isAssignableFrom(remoteObj.getClass())) {
				IUserRMIService service = (IUserRMIService) remoteObj;
				System.out.println(service.getName(1));
				System.out.println(service.getAllUsers().get(1).getAddress());
			} else if (RmiInvocationHandler.class.isAssignableFrom(remoteObj.getClass())) {
				// Spring 注册的RMI服务如果没有 extends Remote接口则无法转换成原始的接口类型
				System.out.println(((RmiInvocationHandler) remoteObj).getTargetInterfaceName());
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	private void testRMIBySpring() {
		Assert.assertNotNull(userRmiService);
		System.out.println(userRmiService.getName(0));
		System.out.println(userRmiService.getAllUsers().get(1).getUserName());
	}

}
