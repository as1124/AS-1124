/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年9月28日
 *******************************************************************************/

package com.volume2.ch4.jdbc;

import java.io.File;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * HUANG 此处填写 class 信息
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */

public class BlobCase {

	static Map<String, File> pluginMap = new HashMap<>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//		String eclipseHome = "D:/eclipse_Kepler/plugins";
		//
		//		new File(eclipseHome).listFiles(file -> {
		//			String fileName = file.getName();
		//			String[] segments = fileName.split("_");
		//			if (pluginMap.containsKey(segments[0])) {
		//				System.out.println(segments[0]);
		//			} else {
		//				pluginMap.put(segments[0], file);
		//			}
		//			return false;
		//		});

		String str = "ed2k://|file|";
		String str2 = "%E6%97%A0%E8%AF%81%E4%B9%8B%E7%BD%AA.EP09.1080P[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1";
		String str3 = "www.6vhao.tv].mp4|853346476|7DF6EBE032A491D7FA9CECF5D0B21F16|h=3HPFTWREJCTVY3OF54AMQA65SFQ5FZQ7|/";
		byte[] arrays = Base64.getDecoder().decode(str);
		System.out.println("huangjw");
	}

}
