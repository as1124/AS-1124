/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年8月9日
 *******************************************************************************/

package com.mobile.document.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.batik.ext.awt.image.codec.FileCacheSeekableStream;
import org.apache.batik.ext.awt.image.codec.tiff.TIFFImageDecoder;
import org.apache.commons.io.FilenameUtils;
import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mobile.document.converter.DocumentServiceConstants;

import net.arnx.wmf2svg.gdi.svg.SvgGdi;
import net.arnx.wmf2svg.gdi.wmf.WmfParser;

/**
 * 图片解析器
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public class PictureTranslator {

	static Logger logger = LoggerFactory.getLogger(PictureTranslator.class);

	private PictureTranslator() {
		// default constructor
	}

	/**
	 * 解析 <code>TIFF</code> 格式图片文件
	 * @param content 输入源
	 * @param image 目标文件, 必须是<strong> <code>png</code> </strong>格式
	 * @return
	 */
	public static String[] translatorTIFF(InputStream content, File image) {
		FileCacheSeekableStream tiffIn = null;
		String[] pathArray = null;
		try {
			tiffIn = new FileCacheSeekableStream(content);
			TIFFImageDecoder tiffDecoder = new TIFFImageDecoder(tiffIn, null);

			int pageSize = tiffDecoder.getNumPages();
			pathArray = new String[pageSize];

			RenderedImage renImage = tiffDecoder.decodeAsRenderedImage(0);
			pathArray[0] = image.getAbsolutePath();
			ImageIO.write(renImage, "png", image);

			String imagePath = image.getAbsolutePath();
			String extension = FilenameUtils.getExtension(imagePath);
			String baseName = FilenameUtils.removeExtension(imagePath);

			for (int i = 1; i < pageSize; i++) {
				renImage = tiffDecoder.decodeAsRenderedImage(i);
				File file2Save = new File(baseName + DocumentServiceConstants.SEGMENT_SEPARATOR + i + "." + extension);
				ImageIO.write(renImage, "png", file2Save);
				pathArray[i] = file2Save.getAbsolutePath();
			}
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		} finally {
			try {
				if (content != null)
					content.close();
				if (tiffIn != null)
					tiffIn.close();
			} catch (IOException e) {
				// do not care
			}
		}
		return pathArray;
	}

	/**
	 * 解析 <code>WMF</code> 格式图片文件
	 * @param content 输入源
	 * @return base64转换后的图片内容字节码
	 */
	public static String translatorWMF(InputStream content) {
		String result = "";
		try {
			// 先解析成svg文件
			WmfParser parser = new WmfParser();
			SvgGdi gdi = new SvgGdi(false);
			parser.parse(content, gdi);

			Document doc = gdi.getDocument();
			NodeList imageNode = doc.getElementsByTagName("image");
			if (imageNode != null && imageNode.getLength() > 0) {
				NamedNodeMap attrs = imageNode.item(0).getAttributes();
				for (int i = 0; i < attrs.getLength(); i++) {
					Node attr = attrs.item(i);
					String name = attr.getNodeName();
					if ("src".equalsIgnoreCase(name) || "xlink:href".equalsIgnoreCase(name)) {
						result = attr.getNodeValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		} finally {
			try {
				if (content != null)
					content.close();
			} catch (IOException e) {
				// it's OK
			}
		}
		return result;
	}

	/**
	 * 解析 <code>EMF</code> 格式图片文件
	 * @param content 输入源
	 * @param image
	 * @return
	 */
	public static String translatorEMF(InputStream content, File image) {
		EMFInputStream eis = null;
		try {
			eis = new EMFInputStream(content, EMFInputStream.DEFAULT_VERSION);
			EMFRenderer emfRenderer = new EMFRenderer(eis);
			int width = (int) eis.readHeader().getBounds().getWidth() + 10;
			int height = (int) eis.readHeader().getBounds().getHeight() + 10;

			// 设置图片的大小和样式
			BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = buffImage.createGraphics();
			emfRenderer.paint(graphics);
			graphics.dispose();

			ImageIO.write(buffImage, FilenameUtils.getExtension(image.getAbsolutePath()), image);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		} finally {
			try {
				if (content != null)
					content.close();
				if (eis != null)
					eis.close();
			} catch (IOException e) {
				// it's OK
			}
		}
		return image.getAbsolutePath();
	}

}
