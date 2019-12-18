package com.as1124.document.converter.impl.poi;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.SlideShowFactory;

import com.as1124.document.converter.AbstractImageConverter;
import com.as1124.document.converter.DocumentConvertException;
import com.as1124.document.converter.DocumentServiceConstants;

/**
 * PowerPoint文档转HTML，文件后缀：<code>ppt</code>。
 * <pre>保存的图片名称规则：
 * ${目标文件名}<code>-${pageIndex}.${目标文件后缀}</code>
 * 
 * 如：{@code onConvert("D:/files/test.ppt", "D:/files/pics/test.png, null)} 
 * 得到的第一张图片路径是：<code>D:/files/pics/test_0.png</code>
 * </pre>
 * 
 * @see DocumentServiceConstants#SEGMENT_SEPARATOR
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class PPT2ImageConverter extends AbstractImageConverter {

	private int pageCount = 0;

	@Override
	public boolean doConvert(File inputSource, File targetFile, Map<String, ?> opts)
			throws IOException, DocumentConvertException {
		// 缩放比例
		float scale = 1;
		if (opts != null && opts.containsKey(OPT_SCALE)) {
			scale = Float.parseFloat(opts.get(OPT_SCALE).toString());
		}

		// 输出格式
		String format = FilenameUtils.getExtension(targetFile.getName());

		SlideShow<?, ?> slideShow = null;
		try {
			slideShow = SlideShowFactory.create(inputSource, null, true);
			List<? extends Slide<?, ?>> slides = slideShow.getSlides();
			setPageCount(slides.size());

			Dimension pageSize = slideShow.getPageSize();
			int width = (int) (pageSize.width * scale);
			int height = (int) (pageSize.height * scale);

			for (int i = 0; i < getPageCount(); i++) {
				Slide<?, ?> slide = slides.get(i);

				BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics2D graphics = img.createGraphics();

				// default rendering options
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				graphics.scale(scale, scale);
				graphics.fill(new Rectangle2D.Float(0, 0, width, height));
				slide.draw(graphics);
				graphics.dispose();

				File destFile = new File(FilenameUtils.removeExtension(targetFile.getAbsolutePath())
						+ DocumentServiceConstants.SEGMENT_SEPARATOR + i + "." + format);
				ImageIO.write(img, format, destFile);
				img.flush();
				notifyListeners(i, getPageCount(), destFile);
			}
		} finally {
			if (slideShow != null) {
				slideShow.close();
			}
		}

		return true;
	}

	@Override
	public boolean isSupported(String inputExtension, String targetExtension) {
		return "ppt".equalsIgnoreCase(inputExtension);
	}

	/**
	 * 获取PPT页数
	 * 
	 * @return Returns the pageCount.
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 设置PPT页数
	 * @param pageCount The pageCount to set.
	 */
	protected void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
