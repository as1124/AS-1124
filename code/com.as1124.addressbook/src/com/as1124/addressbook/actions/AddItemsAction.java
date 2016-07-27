package com.as1124.addressbook.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.as1124.addressbook.view.AddressbookView;
import com.as1124.addressbook.view.model.AddressManager;

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
		// 模型中要添加数据
		// 视图上要刷新数据
		AddressManager manager = AddressManager.getManager();
		manager.addAddresses(manager.getAddresses());
		view.getViewer().refresh();
	}
	
}
