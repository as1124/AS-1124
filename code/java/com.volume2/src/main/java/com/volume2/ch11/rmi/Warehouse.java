package com.volume2.ch11.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The remote interface for a simple warehouse.
 * RMI必须抛出RemoteException
 * 
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public interface Warehouse extends Remote {

	public double getPrice(String description) throws RemoteException;

	public Product getProduct(List<String> keywords) throws RemoteException;
}
