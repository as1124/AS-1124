package com.as1124.addressbook.view.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 地址本视图模型管理器
 * 
 * @author as1124(mailto:as1124huang@gmail.com)
 *
 */
public class AddressManager {
	
	private static AddressManager manager = new AddressManager();
	
	private List<AddressManagerListener> listeners; 
	
	private List<AddressItem> items;
	
	protected AddressManager() {
		this.listeners = new ArrayList<AddressManagerListener>();
		this.items = new ArrayList<AddressItem>();
		loadAddresses();
	}
	
	protected void loadAddresses(){
		items.add(new AddressItem("Brighter", AddressCategory.ORDINARY));
		items.add(new AddressItem("Denny", AddressCategory.BUSINESS));
		items.add(new AddressItem("Dingding", AddressCategory.VIP));
		items.add(new AddressItem("Flysky", AddressCategory.FRIENDS));
		items.add(new AddressItem("Lily", AddressCategory.TEACHER));
		items.add(new AddressItem("Nemo", AddressCategory.FAMILY));
		items.add(new AddressItem("Snow", AddressCategory.LOVER));
		items.add(new AddressItem("Rainny", AddressCategory.UNKNOWN));
	}
	
	/**
	 * @return
	 */
	public AddressItem[] getAddresses(){
		return this.items.toArray(new AddressItem[]{});
	}
	
	public void removeAddresses(AddressItem[] items){
		
	}
	
	public void addAddressManagerListener(AddressManagerListener listener){
		this.listeners.add(listener);
	}
	
	public void removeAddressManagerListener(AddressManagerListener listener){
		this.listeners.remove(listener);
	}
	
	public void fireAddressesChanged(AddressManagerEvent event){
		for(AddressManagerListener listener : listeners){
			listener.addressesChanged(event);
		}
	}

	public static AddressManager getManager(){
		return manager;
	}
}
