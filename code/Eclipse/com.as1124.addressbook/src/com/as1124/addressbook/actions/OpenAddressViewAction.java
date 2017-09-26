package com.as1124.addressbook.actions;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

import com.as1124.addressbook.AddressbookPlugin;
import com.as1124.addressbook.view.AddressbookView;

/**
 * 打开地址本视图action
 * 
 * @author as1124huang@gmail.com
 *
 */
public class OpenAddressViewAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window = null;
	
	@Override
	public void run(IAction action) {
		if(this.window == null)
			return;
		
		IWorkbenchPage page = window.getActivePage();
		if(page == null)
			return;
		try {
			page.showView(AddressbookView.VIEW_ID);
		} catch (PartInitException e) {
			IStatus status = new Status(IStatus.ERROR, AddressbookPlugin.PLUGIN_ID, 
					e.getMessage(), e);
			Platform.getLog(Platform.getBundle(AddressbookPlugin.PLUGIN_ID)).log(status);
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

}
