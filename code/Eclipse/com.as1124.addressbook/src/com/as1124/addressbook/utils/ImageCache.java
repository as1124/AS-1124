package com.as1124.addressbook.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;

import com.as1124.addressbook.AddressbookPlugin;

/**
 * 图片资源缓存器
 * 
 * @author as1124huang@gmail.com
 *
 */
public class ImageCache {

	Map<String, Image> cache = new HashMap<String, Image>();

	private static ImageCache instance = new ImageCache();

	public static ImageCache getInstance() {
		return instance;
	}

	/**
	 * @param key
	 * @return
	 */
	public Image getImage(String key) {
		if (cache.containsKey(key))
			return cache.get(key);
		Image img = AddressbookPlugin.getImageDescriptor(key).createImage();
		cache.put(key, img);
		return img;
	}

}
