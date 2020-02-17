package com.volume2.ch11.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The remote interface for a simple warehouse to supply service.
 * RMI 服务必须抛出RemoteException
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public interface IWarehouse extends Remote {

	public double getPrice(String description) throws RemoteException;

	public Product getProduct(List<String> keywords) throws RemoteException;
}
