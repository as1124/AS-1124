package com.as1124.addressbook.view;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

/**
 * 地址本视图
 * 
 * @author as1124(mailto:as1124huang@gmail.com)
 *
 */
public class AddressbookView extends ViewPart {

	public static final String ID = "com.as1124.views.AddressbookView";

	private TableViewer viewer;

	public AddressbookView() {
		
	}

	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		TableColumn nameColumn = new TableColumn(viewer.getTable(), SWT.CENTER|SWT.Selection);
		nameColumn.setText("姓名");
		nameColumn.setWidth(100);
		nameColumn.setMoveable(true);
		nameColumn.setResizable(true);
		
		TableColumn categoryColumn = new TableColumn(viewer.getTable(), SWT.CENTER|SWT.Selection);
		categoryColumn.setText("类别");
		categoryColumn.setWidth(200);
		categoryColumn.setMoveable(false);
		categoryColumn.setResizable(false);
		categoryColumn.addControlListener(new ControlListener() {
			
			public void controlResized(ControlEvent e) {
				
			}
			
			public void controlMoved(ControlEvent e) {
				
			}
		});
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		viewer.setContentProvider(new AddressviewContentProvider());
		viewer.setLabelProvider(new AddressviewLabelProvider());
		viewer.setInput(getViewSite());
		
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				AddressbookView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		
	}

	private void fillContextMenu(IMenuManager manager) {
		
		// Other plug-ins can contribute there actions here
		//manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		
	}

	private void makeActions() {
		
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
