package com.primeton.rcp.application;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * 工作台窗口配置器
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(800, 600));
		
		configurer.setShowCoolBar(false);
		configurer.setShowFastViewBars(false);
		configurer.setShowMenuBar(true);
		configurer.setShowPerspectiveBar(false);
		configurer.setShowProgressIndicator(true);
		configurer.setShowStatusLine(false);
		
		configurer.setTitle("点餐系统");
		configurer.setShellStyle(SWT.NO_TRIM | SWT.RESIZE | SWT.TITLE | SWT.TOOL | SWT.SHEET 
				| SWT.APPLICATION_MODAL | SWT.MODELESS | SWT.PRIMARY_MODAL | SWT.SYSTEM_MODAL | SWT.INHERIT_DEFAULT );
		
	}
	
	public void postWindowOpen() {
		super.postWindowOpen();
		
		Shell shell = getWindowConfigurer().getWindow().getShell();
		shell.setMaximized(true);
		
		//设置窗体的透明度
		shell.setAlpha(230);
		//shell.setFullScreen(true);
		shell.setTouchEnabled(true);
	}
}
