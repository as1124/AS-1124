package com.as1124.addressbook.view;

import java.util.Comparator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.TableColumn;

/**
 * 地址本视图查看器内容排序
 * @author as1124huang@gmail.com
 *
 */
public class AddressViewerSorter extends ViewerSorter {

	//ATTENTION 排序的实现
	
	public AddressViewerSorter(TableColumn[] columns, Comparator[] comparator) {
	}
	
	public int compare(Viewer viewer, Object e1, Object e2) {
		return super.compare(viewer, e1, e2);
	}
}
