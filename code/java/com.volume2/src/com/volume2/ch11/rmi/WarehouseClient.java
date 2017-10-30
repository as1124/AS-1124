package com.volume2.ch11.rmi;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.logging.Level;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.java.core.log.JavaCoreLogger;

/**
 * A client that invokes a remote method.
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public class WarehouseClient {

	public static void main(String[] args) throws NamingException, RemoteException {
		// 为了实现动态类加载（从远程现在class文件），需要设置SecurityManager
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new SecurityManager());

		// 构建一个命名上下文，用来访问RMI注册表
		Context namingContext = new InitialContext();

		System.out.println("RMI registry bindings: ");
		// 返回一个枚举列表, 其中列出了所有匹配的绑定对象。使用"rmi:"调用该方法列出所有RMI对象
		NamingEnumeration<NameClassPair> remoteServices = namingContext.list("rmi://localhost");
		while (remoteServices.hasMoreElements()) {
			NameClassPair service = remoteServices.next();
			System.out.println(service.getName() + ", class=" + service.getClassName());
		}

		String url = "rmi://localhost/central_warehouse";
		try {
			Warehouse centralHouse = (Warehouse) namingContext.lookup(url);
			double price = centralHouse.getPrice("Blackwell Toaster");
			System.out.println("Price = " + price);

			Product info = centralHouse.getProduct(Arrays.asList(new String[] {}));
			System.out.println(info);
		} catch (NamingException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		} catch (RemoteException e) {
			JavaCoreLogger.log(Level.WARNING, e.getMessage(), e);
		}
	}

}
