package com.as1124.addressbook.view;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The <code>LabelProvider</code> of the view.
 * 
 * @author as1124(mailto:as1124huang@gmail.com)
 *
 */
public class AddressviewLabelProvider extends LabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		
		return element.toString();
	}

}
