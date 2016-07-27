package com.as1124.addressbook.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.as1124.addressbook.view.AddressbookView;
import com.as1124.addressbook.view.model.AddressManager;

/**
 * 删除地址本条目操作
 * 
 * @author as1124huang@gmail.com
 *
 */
public class DeleteAddressAction extends Action {
	
	private AddressbookView view;
	
	public DeleteAddressAction(AddressbookView view, String text, ImageDescriptor img) {
		super(text, img);
		this.view = view;
	}

	public void run() {
		AddressManager.getManager().removeAddresses(view.getSelectedAddresses());
		view.getViewer().refresh();
	}
	
}
