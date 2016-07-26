package com.as1124.addressbook.view.model;

import java.util.EventObject;

/**
 * 模型变更事件
 * 
 * @author as1124huang@gmail.com
 *
 */
public class AddressManagerEvent extends EventObject{

	private static final long serialVersionUID = 833058943196363247L;

	public AddressManagerEvent(Object source) {
		super(source);
		
	}

}
