package com.primeton.rcp.application;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 * 
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction aboutAction;
	
	private IWorkbenchAction selectAllAction;
	
	private IWorkbenchAction copyAction;
	
	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	/**
	 * 
	 * 系统自动调用，顺序优于一下的几个方法
	 * 
	 */
	protected void makeActions(IWorkbenchWindow window) {
		super.makeActions(window);
		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);
		
		selectAllAction = ActionFactory.SELECT_ALL.create(window);
		copyAction = ActionFactory.COPY.create(window);
		register(copyAction);
		register(selectAllAction);
	}
	
	protected void fillMenuBar(IMenuManager menuBar) {
		super.fillMenuBar(menuBar);
		
		menuBar.add(aboutAction);
	}
	
	protected void fillCoolBar(ICoolBarManager coolBar) {
		super.fillCoolBar(coolBar);
		
		coolBar.add(copyAction);
		coolBar.add(selectAllAction);
	}
	
	protected void fillStatusLine(IStatusLineManager statusLine) {
		super.fillStatusLine(statusLine);
	}
	
}
