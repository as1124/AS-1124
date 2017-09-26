package com.as1124.addressbook.view.model;

import java.util.EventObject;

/**
 * 模型变更事件
 * 
 * @author as1124huang@gmail.com
 *
 */
public class AddressManagerEvent extends EventObject{

	private AddressItem oldValue, newValue;
	
	private String action;
	
	private static final long serialVersionUID = 833058943196363247L;

	public AddressManagerEvent(Object source) {
		super(source);
	}

	/**
	 * @see oldValue
	 * @return the oldValue
	 */
	public AddressItem getOldValue() {
		return oldValue;
	}

	/**
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(AddressItem oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * @see newValue
	 * @return the newValue
	 */
	public AddressItem getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(AddressItem newValue) {
		this.newValue = newValue;
	}

	/**
	 * @see action
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

}
