package com.as1124.addressbook.view.model;

/**
 * 模型变更监听器
 * 
 * @author as1124huang@gmail.com
 *
 */
public interface AddressManagerListener {

	public void addressesChanged(AddressManagerEvent event);
}
