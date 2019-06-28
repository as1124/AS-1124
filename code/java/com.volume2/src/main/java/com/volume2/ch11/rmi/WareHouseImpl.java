package com.volume2.ch11.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is the implementation for the remote Warehouse interface.<br/>
 * 通过继承{@link UnicastRemoteObject}可以方便的暴露远程服务，具体方法请参考其JavaDoc
 * 
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public class WareHouseImpl extends UnicastRemoteObject implements Warehouse {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3419605232274679110L;

	private transient Map<String, Product> products = new HashMap<>();

	private transient Warehouse backup = null;

	/**
	 * 调用父类构造方法暴露Remote服务
	 * 
	 * @throws RemoteException
	 */
	public WareHouseImpl() throws RemoteException {
		super();
	}

	public WareHouseImpl(Warehouse backup) throws RemoteException {
		super();
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
