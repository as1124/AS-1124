package com.as1124.addressbook.view.model;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.as1124.addressbook.AddressbookMessages;

/**
 * @author as1124huang@gmail.com
 *
 */
public class AddressItemPropertySource implements IPropertySource {

	protected static final String PROPERTY_CATEGORY = "com.as1124.addressbook.properties.category";
	
	protected static final String PROPERTY_NAME = "com.as1124.addressbook.properties.name";
	
	private ComboBoxPropertyDescriptor categoryDescriptor;
	
	private TextPropertyDescriptor nameDescriptor;
	
	private AddressItem item;
	
	public AddressItemPropertySource(AddressItem address) {
		this.item = address;
	}
	
	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		String[] categories = AddressCategory.getCategoryMap().keySet().toArray(new String[]{});
		categoryDescriptor = new ComboBoxPropertyDescriptor(PROPERTY_CATEGORY, 
				AddressbookMessages.VIEW_COLUMN_CATEGORY, categories);
		categoryDescriptor.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element) {
				return super.getText(element);
			}
		});
		
		nameDescriptor = new TextPropertyDescriptor(PROPERTY_NAME, AddressbookMessages.VIEW_COLUMN_NAME);
		nameDescriptor.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element) {
				return super.getText(element);
			}
		});
		
		return new IPropertyDescriptor[]{categoryDescriptor, nameDescriptor};
	}

	@Override
	public Object getPropertyValue(Object id) {
		if(id.equals(PROPERTY_CATEGORY)){
			return item.getCategory().getPriority();
		} else if(id.equals(PROPERTY_NAME)){
			return item.getName();
		} else {
			return "";
		}
	}

	@Override
	public boolean isPropertySet(Object id) {
		// HUANG Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// HUANG Auto-generated method stub

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// HUANG 值变更了但是没有通知模型, 视图也没有响应刷新
		if(id.equals(PROPERTY_CATEGORY)){
			//
		} else if(id.equals(PROPERTY_NAME)){
			item.setName(value.toString());
		}

	}

}
