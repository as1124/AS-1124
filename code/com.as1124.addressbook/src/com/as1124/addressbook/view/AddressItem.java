package com.as1124.addressbook.view;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class AddressItem {
	
	private String name;
	
	private AddressCategory category;
	
	public AddressItem() {
	}
	
	public static void main(String[] args){
		String path = "D:/eclipse_4.3.2_android/androidWorkspace";
		try {
			FileUtils.deleteDirectory(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
