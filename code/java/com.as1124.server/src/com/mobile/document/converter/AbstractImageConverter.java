/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年8月16日
 *******************************************************************************/

package com.mobile.document.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 目标文件为图片类型或者PDF转换器标志接口
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public abstract class AbstractImageConverter extends AbstractDocumentConverter {

	/**
	 * Comment for <code>OPT_SCALE</code>
	 * 设置文档转换过程中的缩放比率
	 * 
	 */
	public static final String OPT_SCALE = "PICTURE_SCALE";

	/**
	 * Comment for <code>OPT_ROTATION</code>
	 * PDF转换图片时的旋转角度
	 */
	public static final String OPT_ROTATION = "PICTURE_ROTATION";

	private List<IImageConvertListener> listeners = new ArrayList<IImageConvertListener>();

	/**
	 * 获取 文件页数
	 * @return 
	 */
	public abstract int getPageCount();

	/**
	 * 添加转换过程监听器
	 * 
	 * @param listener
	 */
	public void addConvertListener(IImageConvertListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * 移除转换过程监听器
	 * 
	 * @param listener
	 */
	public void removeConvertListener(IImageConvertListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * 通知监听器当前文件转成图片的进度
	 * 
	 * @param current 当前转换完成的图片下标
	 * @param total 总图片/页 数
	 * @param image 当前转换完成的图片文件
	 */
	protected void notifyListeners(final int current, final int total, final File image) {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(IImageConvertListener listener : listeners) {
					listener.onPageComplete(current, total, image);
				}
			}
		});
		thread.start();
	}
	
	/**
	 * @return Returns the listeners.
	 */
	public List<IImageConvertListener> getListeners() {
		return listeners;
	}

}
