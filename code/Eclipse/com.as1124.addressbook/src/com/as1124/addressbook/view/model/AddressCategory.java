package com.as1124.addressbook.view.model;

import java.util.HashMap;

import org.eclipse.swt.graphics.Image;

import com.as1124.addressbook.AddressbookMessages;
import com.as1124.addressbook.ImageKeys;
import com.as1124.addressbook.utils.ImageCache;

/**
 * 地址本视图，人员类别
 * 
 * @author as1124huang@gmail.com
 *
 */
public abstract class AddressCategory implements Comparable<AddressCategory> {

	/**
	 * 类别名称
	 */
	private String categoryName;

	/**
	 * 类别等级
	 */
	private int priority;

	private static final HashMap<String, AddressCategory> categoryMap = 
			new HashMap<String, AddressCategory>();

	public AddressCategory(String name, int priority) {
		this.categoryName = name;
		this.priority = priority;
	}

	/**
	 * 获取类别图标
	 * @return
	 */
	public abstract Image getImage();

	/**
	 * @see #categoryName
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the {@link #categoryName} to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @see #priority
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the {@link #priority} to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int compareTo(AddressCategory another) {
		return this.priority - another.priority;
	}

	public static final AddressCategory UNKNOWN = new AddressCategory(AddressbookMessages.CATEGORY_UNKNOWN, 0) {
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.IMG_CAT_UNKNOWN);
		}
	};
	public static final AddressCategory ORDINARY = new AddressCategory(AddressbookMessages.CATEGORY_ORDINARY, 1) {
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.IMG_CAT_ORDINARY);
		}
	};
	public static final AddressCategory MATE = new AddressCategory(AddressbookMessages.CATEGORY_MATE, 2) {
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.IMG_CAT_MATE);
		}
	};
	public static final AddressCategory BUSINESS = new AddressCategory(AddressbookMessages.CATEGORY_BUSINESS, 3) {
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.IMG_CAT_BUSINESS);
		}
	};
	public static final AddressCategory FRIENDS = new AddressCategory(AddressbookMessages.CATEGORY_FRIENDS, 4) {
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.IMG_CAT_FRIENDS);
		}
	};

	public static final AddressCategory FAMILY = new AddressCategory(AddressbookMessages.CATEGORY_FAMILY, 5) {
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.IMG_CAT_FAMILY);
		}
	};
	public static final AddressCategory VIP = new AddressCategory(AddressbookMessages.CATEGORY_VIP, 6) {
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.IMG_CAT_VIP);
		}
	};
	public static final AddressCategory TEACHER = new AddressCategory(AddressbookMessages.CATEGORY_TEACHER, 7) {
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.IMG_CAT_TEACHER);
		}
	};
	public static final AddressCategory LOVER = new AddressCategory(AddressbookMessages.CATEGORY_LOVER, 8) {
		public Image getImage() {
			return ImageCache.getInstance().getImage(ImageKeys.IMG_CAT_LOVER);
		}
	};
	
	//类型查找
	private static final AddressCategory [] TYPES = {
		UNKNOWN,
		ORDINARY,
		MATE,
		BUSINESS,
		FRIENDS,
		FAMILY,
		VIP,
		TEACHER,
		LOVER,
	};

	public static AddressCategory[] getTypes(){
		return TYPES;
	}
	
	public static HashMap<String, AddressCategory> getCategoryMap(){
		if(categoryMap.isEmpty()) {
			categoryMap.put(UNKNOWN.getCategoryName(), UNKNOWN);
			categoryMap.put(ORDINARY.getCategoryName(), ORDINARY);
			categoryMap.put(MATE.getCategoryName(), MATE);
			categoryMap.put(BUSINESS.getCategoryName(), BUSINESS);
			categoryMap.put(FRIENDS.getCategoryName(), FRIENDS);
			categoryMap.put(FAMILY.getCategoryName(), FAMILY);
			categoryMap.put(VIP.getCategoryName(), VIP);
			categoryMap.put(TEACHER.getCategoryName(), TEACHER);
			categoryMap.put(LOVER.getCategoryName(), LOVER);
		}
		return categoryMap;
	}
}
