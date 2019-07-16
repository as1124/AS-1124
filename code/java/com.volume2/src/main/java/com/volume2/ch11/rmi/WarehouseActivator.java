package com.volume2.ch11.rmi;

import java.io.File;
import java.io.IOException;
import java.rmi.MarshalledObject;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationDesc;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationGroup;
import java.rmi.activation.ActivationGroupDesc;
import java.rmi.activation.ActivationGroupID;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * This server program instantiates a remote warehouse object, registers it 
 * with the naming service, and waits for clients to invoke methods.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WarehouseActivator {

	public static void main(String[] args) throws NamingException, IOException, ActivationException {
		System.out.println("Constructing activation descriptors...");

		Properties pros = new Properties();
		// use the server.policy file in the current directory
		pros.put("java.security.policy", new File("server.policy").getCanonicalPath());
		ActivationGroupDesc group = new ActivationGroupDesc(pros, null);
		ActivationGroupID id = ActivationGroup.getSystem().registerGroup(group);

		Map<String, Double> prices = new HashMap<>();
		prices.put("Blackwell Toaster", 24.95);
		prices.put("ZapXpress", 49.95);
		MarshalledObject<Object> param = new MarshalledObject<>(prices);

		String codebase = "";

		//ATTENTION 测试这里的className和location是不是一起配合使用的
		ActivationDesc desc = new ActivationDesc(id, "WarehouseImpl", codebase, param);
		Warehouse centralWarehouse = (Warehouse) Activatable.register(desc);

		System.out.println("Binding activable implementation to registry...");
		Context namingContext = new InitialContext();
		namingContext.bind("rmi://localhost:9090/central_warehouse", centralWarehouse);
		System.out.println("Exiting...");

	}

}
