package com.volume2.ch11.rmi.server;

import java.io.File;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.MarshalledObject;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationDesc;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationGroup;
import java.rmi.activation.ActivationGroupDesc;
import java.rmi.activation.ActivationGroupID;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.volume2.ch11.rmi.IWarehouse;
import com.volume2.ch11.rmi.WarehouseImpl2;

/**
 * This server program instantiates a remote warehouse object, registers it 
 * with the naming service, and waits for clients to invoke methods.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WarehouseActivator {

	public static void main(String[] args) throws ActivationException, AlreadyBoundException, IOException {
		System.out.println("Constructing activation descriptors...");

//		System.setProperty("java.security.policy", "rmid.policy");
//		System.setSecurityManager(new SecurityManager());
		Properties pros = new Properties();
		// use the server.policy file in the current directory
//		pros.put("java.security.policy", new File("rmid.policy").getCanonicalPath());
		ActivationGroupDesc group = new ActivationGroupDesc(pros, null);
		ActivationGroupID id = ActivationGroup.getSystem().registerGroup(group);

		Map<String, Double> prices = new HashMap<>();
		prices.put("Blackwell Toaster", 24.95);
		prices.put("ZapXpress", 49.95);
		MarshalledObject<Map<String, Double>> param = new MarshalledObject<>(prices);

		String codebase = "file:///Users/work/rmi-service.jar";
		ActivationDesc desc = new ActivationDesc(id, WarehouseImpl2.class.getName(), codebase, param);
		IWarehouse centralWarehouse = (IWarehouse) Activatable.register(desc);

		System.out.println("Binding activable implementation to registry...");
		Registry rmiRegistry = LocateRegistry.createRegistry(9090);
		rmiRegistry.bind("central_warehouse2", centralWarehouse);
		System.out.println("Activable Waitting for client call...");
	}

}
