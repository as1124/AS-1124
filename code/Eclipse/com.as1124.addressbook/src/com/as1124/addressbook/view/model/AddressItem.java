package com.as1124.addressbook.view.model;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 地址本视图Item
 * 
 * @author as1124huang@gmail.com
 *
 */
public class AddressItem implements IAdaptable {
	
	/**
	 * 姓名
	 */
	private String name;
	
	private String messageInfo;
	
	/**
	 * 类别
	 */
	private AddressCategory category;
	
	public AddressItem() {
	}

	public AddressItem(String name, AddressCategory category){
		this.name = name;
		this.category = category;
	}
	
	/**
	 * @see name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see messageInfo
	 * @return the messageInfo
	 */
	public String getMessageInfo() {
		return messageInfo;
	}

	/**
	 * @param messageInfo the messageInfo to set
	 */
	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}

	/**
	 * @see category
	 * @return the category
	 */
	public AddressCategory getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(AddressCategory category) {
		this.category = category;
	}

	public Object getAdapter(Class adapter) {
		if(adapter == IPropertySource.class){
			return new AddressItemPropertySource(this);
		}
		return null;
	}
	
}
