package com.as1124.addressbook;

import org.eclipse.osgi.util.NLS;

public class AddressbookMessages extends NLS {
	private static final String BUNDLE_NAME = "com.as1124.addressbook.messages_zh_CN"; //$NON-NLS-1$
	public static String ACTION_ADD;
	public static String ACTION_DEL;
	public static String ACTION_FILTER;
	public static String ACTION_FILTER_DIALOG;
	public static String ACTION_FILTER_DIALOG_TIP;
	public static String CATEGORY_BUSINESS;
	public static String CATEGORY_FAMILY;
	public static String CATEGORY_FRIENDS;
	public static String CATEGORY_LOVER;
	public static String CATEGORY_MATE;
	public static String CATEGORY_ORDINARY;
	public static String CATEGORY_TEACHER;
	public static String CATEGORY_UNKNOWN;
	public static String CATEGORY_VIP;
	public static String VIEW_COLUMN_CATEGORY;
	public static String VIEW_COLUMN_NAME;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, AddressbookMessages.class);
	}

	private AddressbookMessages() {
	}
}
