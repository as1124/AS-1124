package com.primeton.rcp.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {
	
	public static final String ID = "com.primeton.rcp.view";

	private Composite leftPanel = null;
	
	private Composite rightPanel = null;
	
	
	public void createPartControl(Composite parent) {
		
		GridData parentData = new GridData();
		parentData.grabExcessHorizontalSpace = true;
		parentData.grabExcessVerticalSpace = true;
		parent.setLayoutData(parentData);
		//parent.setBackground(new Color(parent.getDisplay(), 0, 0, 255));
		
		
		GridLayout layout = new GridLayout(6, true);
		parent.setLayout(layout);
		
		GridData leftData = new GridData(GridData.FILL_BOTH);
		leftData.horizontalSpan = 2;
		leftPanel = new Composite(parent, SWT.NONE);
		leftPanel.setBackground(new Color(parent.getDisplay(), new RGB(255, 255, 255)));
		leftPanel.setLayoutData(leftData);
		leftPanel.setLayout(new GridLayout(1, false));
		
		rightPanel = new Composite(parent, SWT.NONE);
		GridData rightData = new GridData(GridData.FILL_BOTH);
		rightData.horizontalSpan = 4;
		rightPanel.setLayoutData(rightData);
		rightPanel.setBackground(new Color(parent.getDisplay(), 0, 255, 0));
		
		new LeftCategoryPanel(getLeftPanel());
		
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		//viewer.getControl().setFocus();
		getLeftPanel().getParent().setFocus();
	}

	/**
	 * @return the leftPanel
	 */
	public Composite getLeftPanel() {
		return leftPanel;
	}

	/**
	 * @return the rightPanel
	 */
	public Composite getRightPanel() {
		return rightPanel;
	}
	
}