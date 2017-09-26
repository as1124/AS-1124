package com.primeton.rcp.application;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.primeton.rcp.RCPActivator;

/**
 * 工作台配置器
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "com.primeton.rcp.perspective";

	/* (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#initialize(org.eclipse.ui.application.IWorkbenchConfigurer)
	 */
	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);
		
		configurer.setSaveAndRestore(true);
		configurer.declareImage("image.share", RCPActivator.getImageDescriptor("/icons/IMG_0009.JPG"), true);
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#preStartup()
	 */
	public void preStartup() {
		super.preStartup();
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#postStartup()
	 */
	public void postStartup() {
		super.postStartup();
		
	}
	
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#saveState(org.eclipse.ui.IMemento)
	 */
	public IStatus saveState(IMemento memento) {
		return super.saveState(memento);
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.application.WorkbenchAdvisor#restoreState(org.eclipse.ui.IMemento)
	 */
	public IStatus restoreState(IMemento memento) {
		return super.restoreState(memento);
		
	}
	
}
