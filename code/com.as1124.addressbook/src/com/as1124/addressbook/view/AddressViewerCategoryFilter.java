package com.as1124.addressbook.view;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.internal.misc.StringMatcher;

import com.as1124.addressbook.view.model.AddressCategory;
import com.as1124.addressbook.view.model.AddressItem;

/**
 * 地址本视图内容过滤器
 * 
 * @author as1124huang@gmail.com
 *
 */
public class AddressViewerCategoryFilter extends ViewerFilter {

	private String pattern;
	
	private StructuredViewer viewer;
	
	public AddressViewerCategoryFilter(StructuredViewer viewer) {
		this.viewer = viewer;
	}
	
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		AddressItem item = (AddressItem) element;
		AddressCategory category = item.getCategory();
		if(this.pattern.isEmpty()){
			return true;
		}
		StringMatcher matcher = new StringMatcher(pattern, true, true);
		return matcher.match(category.getCategoryName());
	}

	/**
	 * @see pattern
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		if(pattern.isEmpty()){
			viewer.removeFilter(this);
			viewer.refresh();
			return;
		}
		this.pattern = pattern;
		viewer.addFilter(this);
	}

	
}
