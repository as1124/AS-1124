package com.volume2.ch11.rmi.server;

import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.java.core.log.JavaCoreLogger;
import com.volume2.ch11.rmi.Product;
import com.volume2.ch11.rmi.WareHouseImpl;

import sun.rmi.transport.Transport;
import sun.rmi.transport.tcp.TCPEndpoint;

/**
 * <b><code>RMI</code> 组成: </b>
 * <ol>
 * <li>RMI-Registry：默认监听端口1099，注册表用于服务管理
 * <li>RMI-Server：远程服务具体实现，通过Proxy（stub）与注册表通信，{@link RemoteRef}
 * <li>RMI-Client：客户端调用起，通过Stub进行服务交互
 * <li>底层传输交互：Tcp/IO模型，{@link Transport}、{@link TCPEndpoint}
 * </ol>
 * 
 * <b>RMI 注册表（{@link Registry}）访问方式</b>：
 * <ol>
 * <li>两种启动：命令行启动、代码启动；命令行时需要指定 RMI-Server 加载类时的codebase
 * <code>-Djava.rmi.server.codebase</code>
 * <li>三种访问途径：{@link InitialContext}，{@link LocateRegistry}，{@link Naming}
 * </ol>
 * 
 * <b>远程服务两种export方式：1-{@link UnicastRemoteObject}，2-{@link Activatable}</b>：
 * <br/><br/>
 * <ol>
 * RMI远程服务激活（Activation）机制
 * <li>启动RMI注册表
 * <li>启动RMI激活守护程序 <code>（RMID服务: rmid -J-Djava.security.policy=rmid.policy）</code>,
 * rmid服务启动需要当前目录下有指定的policy文件
 * <li>注册并绑定服务
 * <li>客户端调用
 * </ol>
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
