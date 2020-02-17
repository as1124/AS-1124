package com.volume2.ch11.rmi.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.java.core.log.JavaCoreLogger;
import com.volume2.ch11.rmi.Product;
import com.volume2.ch11.rmi.WareHouseImpl;

/**
 * This server program instantiates a remote warehouse object, registers 
 * it with the naming service, and waits for clients to invoke methods.
 * <br/>
 * <code>RMI</code> 组成: RMI-Registry，RMI-Server，RMI-Client，Proxy
 * <ul>
 * <li>RMI注册表默认：使用1099端口，只支持本地单播服务</li>
 * <li>RMI 注册表2种启动：命令行启动、代码启动 </li>
 * <li>命令行时需要指定 RMI-Server 加载类的codebase：<code>-Djava.rmi.server.codebase</code>
 * <li>{@link InitialContext}，{@link LocateRegistry} 两种方式都可访问 RMI 注册表
 * <li>RMI 服务对象2种实现：1-继承 {@link UnicastRemoteObject} 接口，2-继承{@link Activatable};
 * 其实都是一个 export 的过程
 * </ul>
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WarehouseServer {

	public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException {
		rmiServerWithSystemCommand();

		rmiServerWithProgramCode();
	}

	/**
	 * 命令行命令启动RMI注册表：<code>rmiregistry</code>，启动时
	 * 需要指定虚拟机 codebase 参数
	 * <br/><br/>
	 * ATTENTION ：在编译的类目录下启动注册表才不报 ClassNotFoundException
	 * 
	 * @throws RemoteException 
	 */
	private static void rmiServerWithSystemCommand() throws RemoteException {
		System.out.println("Constructing server implementation...");

		WareHouseImpl centralImpl = new WareHouseImpl();
		centralImpl.add("toaster", new Product("Blackwell Toaster", 23.95));
		centralImpl.add("java", new Product("Core Java vol.2", 49.95));

		System.out.println("Binding server implementation to registry...");
		Context namingContext;
		try {
			namingContext = new InitialContext();
			// 默认查找 1099 端口
			namingContext.bind("rmi:central_warehouse", centralImpl);
		} catch (NamingException e) {
			JavaCoreLogger.log(Level.SEVERE, "!!!!  RMI-Registry 注册表服务未启动或远程服务绑定异常 !!!!", e);
		}

		System.out.println("Waiting for invocations from clients...");
	}

	/**
	 * @throws RemoteException
	 * @throws NamingException
	 * @throws AlreadyBoundException 
	 */
	private static void rmiServerWithProgramCode() throws RemoteException, NamingException, AlreadyBoundException {
		System.setProperty("java.security.policy", "server.policy");
		System.setSecurityManager(new SecurityManager());

		WareHouseImpl backupWarehouse = new WareHouseImpl();
		WareHouseImpl centralImpl = new WareHouseImpl(backupWarehouse);
		centralImpl.add("toaster", new Product("Blackwell Toaster", 23.95));
		centralImpl.add("java", new Product("Core Java vol.2", 49.95));

		System.out.println("Binding server implementation to registry...");
		// 代替命令行启动RMI Registry("rmiregistry")
		Registry rmiRegistry = null;
		try {
			rmiRegistry = LocateRegistry.createRegistry(9090);
		} catch (RemoteException e) {
			System.out.println("!!!! 程序启动 RMI-Registry 失败!!!!");
		}
		String serviceName = "rmi://127.0.0.1:9090/central_warehouse";
		if (rmiRegistry != null) {
			rmiRegistry.bind("central_warehouse", centralImpl);
		} else {
			Context namingContext = new InitialContext();
			namingContext.bind(serviceName, centralImpl);
		}
		System.out.println("Waiting for invocations from clients...");
	}

}
