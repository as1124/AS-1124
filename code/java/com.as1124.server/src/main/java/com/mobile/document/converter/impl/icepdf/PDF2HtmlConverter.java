/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 *******************************************************************************/

package com.mobile.document.converter.impl.icepdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import com.mobile.document.converter.DocumentConvertException;
import com.mobile.document.converter.DocumentServiceConstants;

/**
 * PDF文档转html处理器。先将PDF转图片，然后用默认html模板生成网页
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */

public class PDF2HtmlConverter extends PDF2ImageConverter {

	@Override
	protected boolean doConvert(File inputSource, File targetFile, Map<?, ?> opts)
			throws IOException, DocumentConvertException {
		String destPath = targetFile.getAbsolutePath();
		String baseFileName = FilenameUtils.removeExtension(destPath);
		String suffix = ".png";

		if (super.doConvert(inputSource, new File(baseFileName + suffix), opts)) {
			StringBuilder resultHTML = new StringBuilder();
			resultHTML.append("<!DOCTYPE html><html><head>");
			resultHTML.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
					+ DocumentServiceConstants.CHARSET_UTF8 + "\">");
			resultHTML.append("</head><body><div style=\"align=\"center\" >");
			for (int i = 0; i < getPageCount(); i++) {
				String imagePath = baseFileName + DocumentServiceConstants.SEGMENT_SEPARATOR + i + suffix;
				resultHTML.append("<img src=\"" + imagePath + "\" />");
			}
			resultHTML.append("</div></body></html>");

			FileOutputStream outStream = null;
			try {
				outStream = new FileOutputStream(targetFile);
				outStream.write(resultHTML.toString().getBytes(DocumentServiceConstants.CHARSET_UTF8));
				outStream.flush();
			} finally {
				if (outStream != null)
					outStream.close();
			}
			return true;
		}

		return false;
	}

	@Override
	public boolean isSupported(String inputExtension, String targetExtension) {
		return ("pdf".equalsIgnoreCase(inputExtension) && "html".equalsIgnoreCase(targetExtension));
	}

	/* (non-Javadoc)
	 * @see com.mobile.document.converter.impl.icepdf.PDF2ImageConverter#onPageComplete(int, int, java.io.File)
	 */
	@Override
	public void onPageComplete(int current, int total, File image) {
		// 是否做分页处理
	}
	
}
