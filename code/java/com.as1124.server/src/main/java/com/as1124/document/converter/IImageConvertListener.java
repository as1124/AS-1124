package com.as1124.document.converter;

import java.io.File;

/**
 * 文档转图片时进度监听器
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public interface IImageConvertListener {

	/**
	 * 文档转图片时监听页面转换完成进度
	 * 
	 * @param current 当前页面下标
	 * @param total 总页数
	 * @param image 当前保存的图片文件
	 */
	public void onPageComplete(int current, int total, File image);

}
