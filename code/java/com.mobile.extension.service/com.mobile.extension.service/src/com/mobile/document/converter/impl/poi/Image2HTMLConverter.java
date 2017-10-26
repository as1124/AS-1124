/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年8月16日
 *******************************************************************************/

package com.mobile.document.converter.impl.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.usermodel.PictureType;

import com.mobile.document.converter.AbstractImageConverter;
import com.mobile.document.converter.DocumentConvertException;
import com.mobile.document.converter.DocumentServiceConstants;
import com.mobile.document.util.PictureTranslator;

/**
 * 图片转HTML
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */

public class Image2HTMLConverter extends AbstractImageConverter {

	private int pageCount = 1;
	
	@Override
	public boolean isSupported(String inputExtension, String targetExtension) {
		return true;
	}

	@Override
	public int getPageCount() {
		return this.pageCount;
	}

	@Override
	protected boolean doConvert(File inputSource, File targetFile, Map<?, ?> opts)
			throws IOException, DocumentConvertException {
		FileInputStream contentIn = null;
		FileOutputStream outStream = null;
		File realImange = null;
		String extension = FilenameUtils.getExtension(inputSource.getAbsolutePath());

		StringBuilder resultHTML = new StringBuilder();
		resultHTML.append("<!DOCTYPE html><html><head>");
		resultHTML.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
				+ DocumentServiceConstants.CHARSET_UTF8 + "\">");
		resultHTML.append("</head><body><div style=\"width: 98%;\"  align=\"center\" >");

		try {
			contentIn = new FileInputStream(inputSource);
			if (PictureType.WMF.getExtension().equalsIgnoreCase(extension)) {
				resultHTML.append("<img src=\"" + PictureTranslator.translatorWMF(contentIn) + "\" />");
			} else if (PictureType.EMF.getExtension().equalsIgnoreCase(extension)) {
				realImange = new File(FilenameUtils.removeExtension(targetFile.getAbsolutePath()) + ".png");
				resultHTML.append("<img src=\"" + PictureTranslator.translatorEMF(contentIn, realImange) + "\" />");
			} else if (PictureType.TIFF.getExtension().equalsIgnoreCase(extension)
					|| "tif".equalsIgnoreCase(extension)) {
				realImange = new File(FilenameUtils.removeExtension(targetFile.getAbsolutePath()) + ".png");
				String[] imgPath = PictureTranslator.translatorTIFF(contentIn, realImange);
				this.pageCount = imgPath.length;
				for (String image : imgPath) {
					resultHTML.append("<img src=\"" + image + "\" />");
				}
			} else {
				resultHTML.append("<img src=\"" + inputSource.getAbsolutePath() + "\" />");
			}
			resultHTML.append("</div></body></html>");

			outStream = new FileOutputStream(targetFile);
			outStream.write(resultHTML.toString().getBytes());
		} finally {
			if (contentIn != null) {
				try {
					contentIn.close();
				} catch (IOException e) {
					// 
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					// 
				}
			}
		}

		return true;
	}

}
