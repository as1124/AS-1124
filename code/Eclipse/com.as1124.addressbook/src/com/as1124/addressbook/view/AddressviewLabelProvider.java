package com.as1124.addressbook.view;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.as1124.addressbook.view.model.AddressItem;

/**
 * The <code>LabelProvider</code> of the view.
 * 
 * @author as1124(mailto:as1124huang@gmail.com)
 *
 */
public class AddressviewLabelProvider extends LabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		switch (columnIndex) {
		case 1:
			return ((AddressItem)element).getCategory().getImage();

		default:
			return null;
		}
	}

	public String getColumnText(Object element, int columnIndex) {
		AddressItem item = (AddressItem) element;
		switch (columnIndex) {
		case 0:
			return item.getName();
		case 1:
			return item.getCategory().getCategoryName();
		default:
			return "";
		}
	}

}
