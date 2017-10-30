package com.volume2.ch11.rmi;

import java.io.IOException;
import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationID;
import java.util.List;
import java.util.Map;

/**
 * This class is implementation for the remote warehouse interface.
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public class WarehouseImpl2 extends Activatable implements Warehouse {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 237288332433063558L;

	private Map<String, Double> prices = null;

	protected WarehouseImpl2(ActivationID ID, MarshalledObject<?> data)
			throws ActivationException, ClassNotFoundException, IOException {
		super(ID, 0);

		prices = (Map<String, Double>) data.get();
	}

	@Override
	public double getPrice(String description) throws RemoteException {
		Double price = prices.get(description);
		return price == null ? 0 : price;
	}

	@Override
	public Product getProduct(List<String> keywords) throws RemoteException {
		return null;
	}

}
