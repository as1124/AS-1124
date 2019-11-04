/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 *******************************************************************************/

package com.mobile.document.converter.impl.poi;

import com.mobile.document.converter.DocumentServiceConstants;

/**
 * PowerPoint文档转HTML，文件后缀：<code>pptx</code>
 * 
 * <pre><strong>
 * 保存的图片名称规则：${目标文件名}<code>-${pageIndex}.${目标文件后缀}</code>
 * 如：{@code onConvert("D:/files/test.pptx", "D:/files/pics/test.png, null)} 
 * 得到的第一张图片路径是：
 * <code>D:/files/pics/test_0.png</code>
 * </strong></pre>
 * 
 * @see PPT2ImageConverter
 * @see DocumentServiceConstants#SEGMENT_SEPARATOR
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class PPTX2ImageConverter extends PPT2ImageConverter {

	@Override
	public boolean isSupported(String inputExtension, String targetExtension) {
		return "pptx".equalsIgnoreCase(inputExtension);
	}

}
