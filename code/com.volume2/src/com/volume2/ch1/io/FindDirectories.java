package com.volume2.ch1.io;

import java.io.File;

/**
 * 文件管理
 * 
 * @author as1124huang@gmail.com
 *
 */
public class FindDirectories {

	public static void main(String[] args) {
		//if no arguments provided, start at the parent directory
		if(args.length <= 0) {
			args = new String[]{"."};
		}
		
		try {
			File pathName = new File(args[0]);
			String[] fileNames = pathName.list();
			
			//enumerate all files in the directory
			for(int i=0; i<fileNames.length; i++){
				File f = new File(pathName.getPath(), fileNames[i]);
				if(f.isDirectory()){
					System.out.println(f.getCanonicalPath());
					//System.out.println(f.toURI());
					main(new String[]{f.getPath()});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

}
