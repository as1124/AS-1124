package com.as1124.document.converter.impl.icepdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.icepdf.core.application.ProductInfo;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import com.as1124.document.converter.AbstractImageConverter;
import com.as1124.document.converter.DocumentConvertException;
import com.as1124.document.converter.DocumentServiceConstants;
import com.as1124.document.converter.IImageConvertListener;

/**
 * PDF文档转图片处理器
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class PDF2ImageConverter extends AbstractImageConverter implements IImageConvertListener {

	/**
	 * Comment for <code>OPT_PRODUCT_INFO</code>：图片水印信息
	 */
	public static final String OPT_PRODUCT_INFO = "PRODUCT_INFO";

	private int pageCount = 0;

	public PDF2ImageConverter() {
		addConvertListener(this);
	}

	/**
	 * @see DocumentServiceConstants#OPT_SCALE
	 * @see DocumentServiceConstants#OPT_ROTATION
	 */
	@Override
	protected boolean doConvert(File inputSource, File targetFile, Map<String, ?> opts)
			throws IOException, DocumentConvertException {
		float scale = 1f;
		float rotation = 0f;
		if (opts != null) {
			if (opts.containsKey(OPT_SCALE)) {
				scale = Float.parseFloat(opts.get(OPT_SCALE).toString());
			}
			if (opts.containsKey(OPT_ROTATION)) {
				rotation = Float.parseFloat(opts.get(OPT_ROTATION).toString());
			}
			if (opts.containsKey(OPT_PRODUCT_INFO)) {
				ProductInfo.COMPANY = opts.get(OPT_PRODUCT_INFO).toString();
			}
		}

		Document document = null;
		try {
			document = new Document();
			document.setFile(inputSource.getAbsolutePath());
			String baseFileName = FilenameUtils.removeExtension(targetFile.getAbsolutePath());
			String extension = FilenameUtils.getExtension(targetFile.getAbsolutePath());
			this.pageCount = document.getNumberOfPages();
			for (int i = 0; i < pageCount; i++) {
				BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
					Page.BOUNDARY_CROPBOX, rotation, scale);
				File file = new File(baseFileName + DocumentServiceConstants.SEGMENT_SEPARATOR + i + "." + extension);
				ImageIO.write(image, extension, file);
				image.flush();
				notifyListeners(i, getPageCount(), file);
			}
		} finally {
			if (document != null)
				document.dispose();
		}
		return true;
	}

	@Override
	public boolean isSupported(String inputExtension, String targetExtension) {
		return "pdf".equalsIgnoreCase(inputExtension);
	}

	/**
	 * 获取 PDF 文件页数
	 * @return Returns the pageCount.
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount The pageCount to set.
	 */
	protected void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public void onPageComplete(int current, int total, File image) {
		// nothing to do
	}

}
