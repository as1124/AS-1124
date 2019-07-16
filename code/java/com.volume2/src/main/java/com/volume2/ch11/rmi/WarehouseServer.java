package com.volume2.ch11.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * This server program instantiates a remote warehouse object, registers 
 * it with the naming service, and waits for clients to invoke methods.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WarehouseServer {

	public static void main(String[] args) throws RemoteException, NamingException {
		System.setProperty("java.security.policy", "server.policy");
		System.setSecurityManager(new SecurityManager());

		// 代替命令行启动RMI Registry("rmiregistry")
		LocateRegistry.createRegistry(9090);

		System.out.println("Constructing server implementation...");
		WareHouseImpl backupWarehouse = new WareHouseImpl();
		WareHouseImpl centralImpl = new WareHouseImpl(backupWarehouse);

		centralImpl.add("toaster", new Product("Blackwell Toaster", 23.95));
		centralImpl.add("java", new Product("Core Java vol.2", 49.95));

		System.out.println("Binding server implementation to registry...");
		Context namingContext = new InitialContext();
		namingContext.rebind("rmi://127.0.0.1:9090/central_warehouse", centralImpl);

		System.out.println("Waiting for invocations from clients...");
	}

}
