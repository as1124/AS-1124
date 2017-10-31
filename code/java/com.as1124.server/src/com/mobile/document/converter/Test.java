/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年8月22日
 *******************************************************************************/

package com.mobile.document.converter;

import java.io.File;
import java.io.IOException;

/**
 * TODO 此处填写 class 信息
 *
 * @author as-1124 (mailto:as-1124@primeton.com)
 */

public abstract class Test {

	public static File inputFile = new File("D:/htmls/java&sonarlint.pdf");
	public static File targetFile = new File("d:/htmls/pics/aaab.png");

	public static IImageConvertListener listener = new IImageConvertListener() {
		
		@Override
		public void onPageComplete(int current, int total, File image) {
			System.out.println("[当前操作页数] "+current);
		}
	};
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		Thread th = new Thread(new Runnable() {

			public void run() {
				try {
					AbstractImageConverter converter = (AbstractImageConverter) DocumentConverterFactory.getConverter(inputFile, targetFile, true);
					converter.addConvertListener(listener);
					converter.convert(inputFile, targetFile);
				} catch (DocumentConvertException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		th.start();
		while(th.isAlive()){
			System.out.println("Alive");
			Thread.sleep(300);
		}
		System.out.println("Done!!!!");
	}

}
