package com.as1124.addressbook.view;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.as1124.addressbook.view.model.AddressManager;

/**
 * The <code>ContentProvider</code> of view.
 * 
 * @author as1124(mailto:as1124huang@gmail.com)
 *
 */
public class AddressviewContentProvider implements IStructuredContentProvider {

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	public Object[] getElements(Object inputElement) {
		return AddressManager.getManager().getAddresses();
	}

}
