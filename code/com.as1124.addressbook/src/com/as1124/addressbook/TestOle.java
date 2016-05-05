package com.as1124.addressbook;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;


public class TestOle {

	protected static OleFrame frame = null;
	
	protected static OleClientSite clientSite = null;
	
	public static void main(String[] args){
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setText("Excel Window");
        shell.setLayout(new FillLayout());
        
        
		frame = new OleFrame(shell, SWT.NONE);
		clientSite = new OleClientSite(frame, SWT.NULL, new File("E:/迅雷下载/玩命速递4.mp4"));
		createMenus(shell);
		clientSite.doVerb(OLE.OLEIVERB_OPEN);
		
		
		
		
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.close();
	}
	
	public static void createMenus(Shell shell){
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem oleMenuItem = new MenuItem(menu, SWT.CASCADE);
		oleMenuItem.setText("OLE");
		frame.setFileMenus(new MenuItem[]{oleMenuItem});
		
		Menu oleMenu = new Menu(oleMenuItem);
		oleMenuItem.setMenu(oleMenu);
		/*
		MenuItem deactivateItem = new MenuItem(oleMenu, SWT.PUSH);
		deactivateItem.setText("Deactivate");
		deactivateItem.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				clientSite.deactivateInPlaceClient();
			}
		});*/
	}
}
