package com.as1124.spring.jmx.remote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;

import org.junit.Assert;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;
import org.springframework.stereotype.Repository;

import com.as1124.spring.jmx.IJmxDoc;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Repository
public class JmxWithRmiRepository {

	@Bean
	public RmiRegistryFactoryBean createRmiRegistry() {
		RmiRegistryFactoryBean rmiRegistry = new RmiRegistryFactoryBean();
		rmiRegistry.setPort(1099);
		return rmiRegistry;
	}

	@Bean
	public ConnectorServerFactoryBean connectorServer() {
		ConnectorServerFactoryBean connectorFactoryBean = new ConnectorServerFactoryBean();
		connectorFactoryBean.setServiceUrl("service:jmx:rmi://localhost/jndi/rmi://localhost:1099/spring");
		return connectorFactoryBean;
	}

	public MBeanServerConnectionFactoryBean mBeanConnectionBean() {
		MBeanServerConnectionFactoryBean serverConnection = new MBeanServerConnectionFactoryBean();
		try {
			serverConnection.setServiceUrl("service:jmx:rmi://localhost/jndi/rmi://localhost:1099/spring");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return serverConnection;

	}

	public static void main(String[] args) {
		try (AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(
				IJmxDoc.class.getPackage().getName())) {
			Assert.assertNotNull(ctx);
			String[] result = Naming.list("rmi://localhost:1099");
			System.out.println(result);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
