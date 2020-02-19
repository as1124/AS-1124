package com.volume2.ch11.rmi.server;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.MarshalledObject;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationDesc;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationGroup;
import java.rmi.activation.ActivationGroupDesc;
import java.rmi.activation.ActivationGroupID;
import java.rmi.registry.LocateRegistry;
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
		prepareRMIServer();

		try {
			callFromClient();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	private static void prepareRMIServer() throws IOException, ActivationException, AlreadyBoundException {
		System.out.println("Constructing activation descriptors...");

		Properties pros = new Properties();
		pros.put("java.security.policy", new File("rmid.policy").getCanonicalPath());
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
		LocateRegistry.getRegistry(1099).bind("activate_warehouse", centralWarehouse);
	}

	private static void callFromClient() throws MalformedURLException, RemoteException, NotBoundException {
		System.out.println("Make a call from client--->");
		Remote remoteObj = Naming.lookup("rmi://localhost:1099/activate_warehouse");
		if (remoteObj != null && IWarehouse.class.isAssignableFrom(remoteObj.getClass())) {
			IWarehouse service = (IWarehouse) remoteObj;
			System.out.println("--->远程调用结果==" + service.getPrice("ZapXpress"));
		}
	}

}
