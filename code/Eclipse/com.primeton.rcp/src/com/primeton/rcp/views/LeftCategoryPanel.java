package com.primeton.rcp.views;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class LeftCategoryPanel {

	
	/**
	 * 
	 */
	public LeftCategoryPanel(Composite control) {
		initControl(control);
	}

	/**
	 * @param control
	 */
	protected void initControl(Composite control) {
		ListViewer viewer = new ListViewer(control, SWT.V_SCROLL);
		List list = viewer.getList();
		
		/*TableColumn column = new TableColumn(table, SWT.BORDER);
		column.setText("菜单类别");
		column.setWidth(200);*/
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_CENTER;
		layoutData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
		list.setLayoutData(layoutData);
		
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(new String[]{"凉菜","热菜","点心","酒水饮料"});
		
	}
	
	class ViewContentProvider implements IStructuredContentProvider {
		
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			
		}

		public void dispose() {
			
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof Object[]) {
				return (Object[]) parent;
			}
	        return new Object[0];
		}
	}

	class ViewLabelProvider extends LabelProvider{
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		public String getText(Object element) {
			return super.getText(element);
		}

		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	
}
