package com.volume2.ch11.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is the implementation for the remote Warehouse interface.
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public class WareHouseImpl extends UnicastRemoteObject implements Warehouse {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3419605232274679110L;

	private transient Map<String, Product> products = null;

	private Warehouse backup = null;

	public WareHouseImpl() throws RemoteException {
		super();
		products = new HashMap<String, Product>();
	}

	public WareHouseImpl(Warehouse backup) throws RemoteException {
		this();
		this.backup = backup;
	}

	@Override
	public double getPrice(String description) throws RemoteException {
		for (Product p : products.values()) {
			if (p.getDescription().equals(description)) {
				Double price = p.getPrice();
				return price == null ? 0 : price;
			}
		}

		if (backup != null)
			return backup.getPrice(description);
		else
			return 0;
	}

	@Override
	public Product getProduct(List<String> keywords) throws RemoteException {
		Product product = null;
		if (keywords.isEmpty()) {
			product = new Book("Java Core Volume2", 20.11);
		} else {
			product = new Product("huang", 123.44);
			product.setLocation(this);
		}
		return product;
	}

	public void add(String keyword, Product product) {
		product.setLocation(this);
		products.put(keyword, product);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
