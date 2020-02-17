package com.volume2.ch11.rmi.client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.logging.Level;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.java.core.log.JavaCoreLogger;
import com.volume2.ch11.rmi.IWarehouse;
import com.volume2.ch11.rmi.Product;

/**
 * A client that invokes a remote method.
 * RMI 注册表访问协议：
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WarehouseClient {

	public static void main(String[] args) throws NamingException {
		try {
			rmiClientTestCommandRegistry();
		} catch (NamingException ex) {
			// nothing
		}

		rmiClientTestProgramCode();
	}

	private static void rmiClientTestCommandRegistry() throws NamingException {
		System.out.println("RMI registry bindings: ");
		// 构建一个命名上下文，用来访问RMI注册表
		Context namingContext = new InitialContext();

		// 返回一个枚举列表, 其中列出了所有匹配的绑定对象。使用"rmi:"调用该方法列出所有RMI对象
		NamingEnumeration<NameClassPair> remoteServices = namingContext.list("rmi://localhost/");
		while (remoteServices.hasMoreElements()) {
			NameClassPair service = remoteServices.next();
			System.out.println(service.getName() + ", class=" + service.getClassName());
		}

		try {
			Object obj = namingContext.lookup("rmi://localhost/central_warehouse");
			if (obj != null && IWarehouse.class.isAssignableFrom(obj.getClass())) {
				IWarehouse centralHouse = (IWarehouse) obj;
				System.out.println("Price = " + centralHouse.getPrice("Blackwell Toaster"));
				Product info = centralHouse.getProduct(Arrays.asList(new String[0]));
				System.out.println(info);
			} else {
				System.out.println("---- 查询的 central_warehouse 远程服务不存在 ---");
			}
		} catch (NamingException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		} catch (RemoteException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private static void rmiClientTestProgramCode() throws NamingException {
		// 为了实现动态类加载（从远程现在class文件），需要设置SecurityManager
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new SecurityManager());

		// 构建一个命名上下文，用来访问RMI注册表
		Context namingContext = new InitialContext();
		System.out.println("RMI registry bindings: ");
		NamingEnumeration<NameClassPair> remoteServices = namingContext.list("rmi://localhost:9090");
		while (remoteServices.hasMoreElements()) {
			NameClassPair service = remoteServices.next();
			System.out.println(service.getName() + ", class=" + service.getClassName());
		}

		try {
			Remote remoteObj = LocateRegistry.getRegistry(9090).lookup("central_warehouse");
			IWarehouse centralHouse = null;
			if (remoteObj != null && IWarehouse.class.isAssignableFrom(remoteObj.getClass())) {
				centralHouse = (IWarehouse) remoteObj;
			} else {
				centralHouse = (IWarehouse) namingContext.lookup("rmi://localhost:9090/central_warehouse");
			}
			System.out.println("Price = " + centralHouse.getPrice("Blackwell Toaster"));

			Product info = centralHouse.getProduct(Arrays.asList(new String[0]));
			System.out.println(info);
		} catch (NamingException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		} catch (RemoteException | NotBoundException e) {
			JavaCoreLogger.log(Level.WARNING, e.getMessage(), e);
		}
	}

}
