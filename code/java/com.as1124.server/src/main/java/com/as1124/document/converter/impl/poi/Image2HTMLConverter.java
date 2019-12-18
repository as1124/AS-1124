package com.as1124.document.converter.impl.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.usermodel.PictureType;

import com.as1124.document.converter.AbstractImageConverter;
import com.as1124.document.converter.DocumentConvertException;
import com.as1124.document.converter.DocumentServiceConstants;
import com.as1124.document.util.PictureTranslator;

/**
 * 图片转HTML
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
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
	protected boolean doConvert(File inputSource, File targetFile, Map<String, ?> opts)
			throws IOException, DocumentConvertException {
		File realImange = null;
		String extension = FilenameUtils.getExtension(inputSource.getAbsolutePath());

		StringBuilder resultHTML = new StringBuilder();
		resultHTML.append("<!DOCTYPE html><html><head>");
		resultHTML.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
				+ DocumentServiceConstants.CHARSET_UTF8 + "\">");
		resultHTML.append("</head><body><div style=\"width: 98%;\"  align=\"center\" >");

		try (FileInputStream contentIn = new FileInputStream(inputSource);
				FileOutputStream outStream = new FileOutputStream(targetFile)) {
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

			outStream.write(resultHTML.toString().getBytes());
		}

		return true;
	}

}
