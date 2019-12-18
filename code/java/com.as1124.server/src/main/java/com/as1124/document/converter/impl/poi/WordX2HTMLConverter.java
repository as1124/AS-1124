package com.as1124.document.converter.impl.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.as1124.document.converter.DocumentConvertException;
import com.as1124.document.util.PictureTranslator;

/**
 * Word文档转HTML，文件后缀：<code>docx</code>
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class WordX2HTMLConverter extends Word2HTMLConverter {

	@Override
	protected boolean doConvert(File inputSource, File targetFile, Map<String, ?> opts)
			throws IOException, DocumentConvertException {
		XWPFDocument document = null;
		try (FileOutputStream fileOut = new FileOutputStream(targetFile)) {
			document = new XWPFDocument(XWPFDocument.openPackage(inputSource.getAbsolutePath()));

			// Directory with the same name of targetFile to save the pictures in document
			File picsDir = new File(FilenameUtils.removeExtension(targetFile.getAbsolutePath()));
			if (!picsDir.exists()) {
				picsDir.mkdirs();
			}

			// Prepare XHTML options (here we set the IURIResolver to load images from a "word/media" folder)
			XHTMLOptions options = XHTMLOptions.create();
			options.URIResolver(new FileURIResolver(picsDir) {

				@Override
				public String resolve(String uri) {
					return translatorPicture(super.resolve(uri));
				}
			});
			options.setExtractor(new FileImageExtractor(picsDir));

			XHTMLConverter.getInstance().convert(document, fileOut, options);
		} catch (XWPFConverterException e) {
			DocumentConvertException dce = new DocumentConvertException(e.getMessage());
			dce.initCause(e.getCause());
			throw dce;
		} finally {
			if (document != null) {
				document.close();
			}
		}
		return true;
	}

	@Override
	public boolean isSupported(String inputExtension, String targetExtension) {
		return ("docx".equalsIgnoreCase(inputExtension) && "html".equalsIgnoreCase(targetExtension));
	}

	/**
	 * 转义部分无法直接预览的格式图片
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	protected String translatorPicture(String filePath) {
		File destFile = null;
		String extension = FilenameUtils.getExtension(filePath);
		try (FileInputStream contentIn = new FileInputStream(filePath)) {
			if (PictureType.WMF.getExtension().equalsIgnoreCase(extension)) {
				return PictureTranslator.translatorWMF(contentIn);
			} else if (PictureType.EMF.getExtension().equalsIgnoreCase(extension)) {
				destFile = new File(FilenameUtils.removeExtension(filePath) + ".png");
				return PictureTranslator.translatorEMF(contentIn, destFile);
			} else if (PictureType.TIFF.getExtension().equalsIgnoreCase(extension)
					|| "tif".equalsIgnoreCase(extension)) {
				destFile = new File(FilenameUtils.removeExtension(filePath) + ".png");

				// 如果tiff文件是多张图片的组合, word也只能显示一张
				return PictureTranslator.translatorTIFF(contentIn, destFile)[0];
			}
		} catch (IOException e) {
			// can not be
		}
		return filePath;
	}

}
