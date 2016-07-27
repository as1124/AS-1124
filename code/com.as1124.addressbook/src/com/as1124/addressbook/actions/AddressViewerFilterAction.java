package com.as1124.addressbook.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Shell;

import com.as1124.addressbook.AddressbookMessages;
import com.as1124.addressbook.view.AddressViewerCategoryFilter;

/**
 * 地址本视图内容过滤器
 * 
 * @author as1124huang@gmail.com
 *
 */
public class AddressViewerFilterAction extends Action {

	private Shell shell;
	
	private AddressViewerCategoryFilter categoryFilter;
	
	public AddressViewerFilterAction(StructuredViewer viewer, String text,
			ImageDescriptor img) {
		super(text, img);
		shell = viewer.getControl().getShell();
		categoryFilter = new AddressViewerCategoryFilter(viewer);
	}
	
	@Override
	public void run() {
		InputDialog dialog = new InputDialog(shell, AddressbookMessages.ACTION_FILTER_DIALOG, 
				AddressbookMessages.ACTION_FILTER_DIALOG_TIP, categoryFilter.getPattern(), null);
		if(dialog.open() == InputDialog.OK){
			String pattern = dialog.getValue();
			if(pattern.isEmpty())
				return;
			categoryFilter.setPattern(pattern);
		}
	}
	
}
