package com.volume2.ch11.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通过继承{@link UnicastRemoteObject}可以方便的暴露远程服务，具体方法请参考其JavaDoc. 
 * UnicastRemoteObject 是单播服务：指通过产生对单一的IP地址和端口的调用来定位远程对象这一事实
 * <br/>
 * 
 * 单播可以理解为：单一服务器对外服务
 * </p>
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WareHouseImpl extends UnicastRemoteObject implements IWarehouse {

	private static final long serialVersionUID = 3419605232274679110L;

	private transient Map<String, Product> products = new HashMap<>();

	private transient IWarehouse backup = null;

	/**
	 * 调用父类构造方法暴露Remote服务
	 * 
	 * @throws RemoteException
	 */
	public WareHouseImpl() throws RemoteException {
		super();
	}

	public WareHouseImpl(IWarehouse backup) throws RemoteException {
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

		if (backup != null) {
			return backup.getPrice(description);
		} else {
			return 0;
		}
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

}
