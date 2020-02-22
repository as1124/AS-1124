package com.volume2.ch4.jdbc;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BlobCase {

	static Map<String, File> pluginMap = new HashMap<>();

	public static void main(String[] args) {
		String eclipseHome = "D:/eclipse_Kepler/plugins";

		new File(eclipseHome).listFiles(file -> {
			String fileName = file.getName();
			String[] segments = fileName.split("_");
			if (pluginMap.containsKey(segments[0])) {
				System.out.println(segments[0]);
			} else {
				pluginMap.put(segments[0], file);
			}
			return false;
		});
	}

}
