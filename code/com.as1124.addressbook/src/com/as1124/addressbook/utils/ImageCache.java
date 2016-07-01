package com.as1124.addressbook.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;

import com.as1124.addressbook.AddressbookPlugin;

public class ImageCache {

	Map<String, Image> cache = new HashMap<String, Image>();

	private static ImageCache instance = new ImageCache();

	public static ImageCache getInstance() {
		return instance;
	}

	public Image getImage(String key) {
		if (cache.containsKey(key))
			return cache.get(key);
		Image img = AddressbookPlugin.getImageDescriptor(key).createImage();
		cache.put(key, img);
		return img;
	}

}
