package com.volume2.ch11.rmi;

import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * This server program instantiates a remote warehouse object, registers 
 * it with the naming service, and waits for clients to invoke methods.
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public class WarehouseServer {

	public static void main(String[] args) throws RemoteException, NamingException {
		System.setProperty("java.security.policy", "server.policy");
		System.setSecurityManager(new SecurityManager());
		
		System.out.println("Constructing server implementation...");
		WareHouseImpl backupWarehouse = new WareHouseImpl();
		WareHouseImpl centralImpl = new WareHouseImpl(backupWarehouse);
		
		centralImpl.add("toaster", new Product("Blackwell Toaster", 23.95));
		centralImpl.add("java", new Product("Core Java vol.2", 49.95));
		
		System.out.println("Binding server implementation to registry...");
		Context namingContext = new InitialContext();
		namingContext.rebind("rmi://localhost/central_warehouse", centralImpl);

		System.out.println("Waiting for invocations from clients...");
	}
	
}

