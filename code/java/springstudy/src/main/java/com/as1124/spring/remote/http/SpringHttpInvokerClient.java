package com.as1124.spring.remote.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import com.as1124.spring.web.model.IUserAction;

/**
 * 模拟客户端调用 Spring-HTTP 远程服务; 值得注意的是：虽然是调用http服务，但是客户端却没有显示的
 * 发送http请求进行处理，这就是Spring-HttpInvoker的特点，和RMI是类似的，关注使用而不关注底层网络交互细节
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class SpringHttpInvokerClient {

	@Autowired
	private IUserAction serviceBean;

	public static void main(String[] args) {
		try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringHttpInvokerClient.class)) {
			SpringHttpInvokerClient testClient = ctx.getBean(SpringHttpInvokerClient.class);
			if (testClient != null) {
				testClient.testSpringHttpInvoker();
			}
			IUserAction service = ctx.getBean(IUserAction.class);
			if (service != null) {
				System.out.println(service.findOne(0));
				System.out.println(service.allUsers().get(1).getUserName());
			}
		}

	}

	@Bean("httpServiceBean")
	public static HttpInvokerProxyFactoryBean createHttpFactoryBean() {
		HttpInvokerProxyFactoryBean factoryBean = new HttpInvokerProxyFactoryBean();
		factoryBean.setServiceInterface(IUserAction.class);

		// 请求服务端获取 HTTP 服务端URL
		factoryBean.setServiceUrl("http://localhost:8080/springstudy/userHttpService");

		return factoryBean;
	}

	private void testSpringHttpInvoker() {
		System.out.println(serviceBean.findOne(0));
		System.out.println(serviceBean.allUsers().get(0).getUserName());
	}

}
