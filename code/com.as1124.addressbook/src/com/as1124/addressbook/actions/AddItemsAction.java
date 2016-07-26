package com.as1124.addressbook.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.as1124.addressbook.view.AddressbookView;

/**
 * 添加地址本条目操作.
 * 
 * @author as1124huang@gmail.com
 *
 */
public class AddItemsAction extends Action {

	private AddressbookView view;
	
	public AddItemsAction(AddressbookView view, String text, ImageDescriptor img) {
		super(text, img);
		this.view = view;
	}
	
	public void run() {
		super.run();
	}
	
}
