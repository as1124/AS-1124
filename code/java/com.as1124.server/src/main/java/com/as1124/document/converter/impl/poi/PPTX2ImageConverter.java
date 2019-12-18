package com.as1124.document.converter.impl.poi;

import com.as1124.document.converter.DocumentServiceConstants;

/**
 * PowerPoint文档转HTML，文件后缀：<code>pptx</code>
 * 
 * <pre> 
 * 保存的图片名称规则：
 * ${目标文件名}<code>-${pageIndex}.${目标文件后缀}</code>
 * 如：{@code onConvert("D:/files/test.pptx", "D:/files/pics/test.png, null)} 
 * 得到的第一张图片路径是：<code>D:/files/pics/test_0.png</code>
 * </pre>
 * 
 * @see PPT2ImageConverter
 * @see DocumentServiceConstants#SEGMENT_SEPARATOR
 * 
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class PPTX2ImageConverter extends PPT2ImageConverter {

	@Override
	public boolean isSupported(String inputExtension, String targetExtension) {
		return "pptx".equalsIgnoreCase(inputExtension);
	}

}
