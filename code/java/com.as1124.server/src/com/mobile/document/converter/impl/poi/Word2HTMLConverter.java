/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年8月9日
 *******************************************************************************/

package com.mobile.document.converter.impl.poi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToHtmlUtils;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobile.document.converter.AbstractTextConverter;
import com.mobile.document.converter.DocumentConvertException;
import com.mobile.document.converter.DocumentServiceConstants;
import com.mobile.document.util.PictureTranslator;

/**
 * Word文档转HTML，文件后缀：<code>doc</code>
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class Word2HTMLConverter extends AbstractTextConverter implements PicturesManager {

	Logger logger = LoggerFactory.getLogger(Word2HTMLConverter.class);

	@Override
	protected boolean doConvert(File inputSource, File targetFile, Map<?, ?> opts)
			throws IOException, DocumentConvertException {
		HWPFDocumentCore wordDocument = null;
		try {
			wordDocument = WordToHtmlUtils.loadDoc(inputSource);
			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
					XMLHelper.getDocumentBuilderFactory().newDocumentBuilder().newDocument());
			wordToHtmlConverter.setPicturesManager(this);
			wordToHtmlConverter.processDocument(wordDocument);

			DOMSource domSource = new DOMSource(wordToHtmlConverter.getDocument());
			StreamResult streamResult = new StreamResult(targetFile);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			String encoding = DocumentServiceConstants.CHARSET_UTF8;
			if (getConvertOpts() != null && getConvertOpts().containsKey(OPT_CHARSET)) {
				encoding = getConvertOpts().get(OPT_CHARSET).toString();
			}
			serializer.setOutputProperty(OutputKeys.ENCODING, encoding);
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(domSource, streamResult);
		} catch (ParserConfigurationException e) {
			DocumentConvertException dce = new DocumentConvertException(e.getMessage());
			dce.initCause(e.getCause());
			throw dce;
		} catch (TransformerConfigurationException e) {
			logger.error(e.getMessage(), e);
		} catch (TransformerException e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}

	@Override
	public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
			float heightInches) {
		File picsDir = new File(FilenameUtils.removeExtension(getTargetFile().getAbsolutePath()));
		if (!picsDir.exists())
			picsDir.mkdirs();

		File destFile = null;
		switch (pictureType) {
			case BMP:
			case GIF:
			case JPEG:
			case PNG:
			case PICT:
				// ATTENTION PICT类型未验证
				destFile = new File(picsDir, suggestedName);
				return saveCommonImage(content, destFile);
			case EMF:
				destFile = new File(picsDir, FilenameUtils.removeExtension(suggestedName) + ".png");
				return PictureTranslator.translatorEMF(new ByteArrayInputStream(content), destFile);
			case WMF:
				return PictureTranslator.translatorWMF(new ByteArrayInputStream(content));
			case TIFF:
				destFile = new File(picsDir, FilenameUtils.removeExtension(suggestedName) + ".png");
				
				// 如果tiff文件是多张图片的组合, word也只能显示一张
				return PictureTranslator.translatorTIFF(new ByteArrayInputStream(content), destFile)[0];
			case UNKNOWN:
			default:
				return MessageFormat.format("[ERROR]Unknow picture-type, {0}({1}).", pictureType.getMime(),
					suggestedName);
		}
	}

	/**
	 * 保存常用格式图片文件
	 * @param content
	 * @param image
	 */
	private String saveCommonImage(byte[] content, File image) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(image);
			out.write(content);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				// do not care
			}
		}
		return image.getAbsolutePath();
	}

	@Override
	public boolean isSupported(String inputExtension, String targetExtension) {
		return ("doc".equalsIgnoreCase(inputExtension) && "html".endsWith(targetExtension));
	}
}
