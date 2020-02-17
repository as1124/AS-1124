package com.volume2.ch11.rmi;

import java.io.IOException;
import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationID;
import java.util.List;
import java.util.Map;

/**
 * This class is implementation for the remote warehouse interface.
 * 通过 {@link Activatable} 实现远程服务，
 * 必须有构造函数 {@link #WarehouseImpl2(ActivationID, MarshalledObject)}: 
 * <p>
 * 一个参数为激活器 ID，一个参数是对象编码包 {@linkplain MarshalledObject}
 * </p>
 * 多个参数可以通过Object[]，或者ArrayList 进行组合后再通过 MarshalledObject 编码传递
 * <br/>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WarehouseImpl2 extends Activatable implements IWarehouse {

	private static final long serialVersionUID = 237288332433063558L;

	private Map<String, Double> prices = null;

	public WarehouseImpl2(ActivationID activationID, MarshalledObject<?> data)
			throws ClassNotFoundException, IOException {
		// 让系统自动分配端口是不是有得挂
		super(activationID, 0);

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
