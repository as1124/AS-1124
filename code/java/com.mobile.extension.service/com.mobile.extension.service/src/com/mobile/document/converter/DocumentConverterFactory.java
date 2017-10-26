/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年8月11日
 *******************************************************************************/

package com.mobile.document.converter;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import com.eos.system.annotation.Bizlet;
import com.mobile.document.converter.impl.icepdf.PDF2HtmlConverter;
import com.mobile.document.converter.impl.icepdf.PDF2ImageConverter;
import com.mobile.document.converter.impl.libre.LibreDocumentConverter;
import com.mobile.document.converter.impl.poi.Excel2HTMLConverter;
import com.mobile.document.converter.impl.poi.Image2HTMLConverter;
import com.mobile.document.converter.impl.poi.PPT2ImageConverter;
import com.mobile.document.converter.impl.poi.PPTX2ImageConverter;
import com.mobile.document.converter.impl.poi.Word2HTMLConverter;
import com.mobile.document.converter.impl.poi.WordX2HTMLConverter;

/**
 * 文档转换器工厂
 * 
 * @author huangjw (mailto:huangjw@primeton.com)
 */
@Bizlet("文档转换服务")
public class DocumentConverterFactory {
	
	/**
	 * Comment for <code>defaultRegistry</code>：默认配置的文档转换器
	 */
	Map<Map.Entry<String, String>, Class<? extends AbstractDocumentConverter>> defaultRegistry = new HashMap<Map.Entry<String, String>, Class<? extends AbstractDocumentConverter>>();

	/**
	 * Comment for <code>userRegistry</code>：用户自定义文档转换器
	 */
	Map<Map.Entry<String, String>, Class<? extends AbstractDocumentConverter>> userRegistry = new HashMap<Map.Entry<String, String>, Class<? extends AbstractDocumentConverter>>();

	static Map<String, Object> opts = new HashMap<String, Object>();

	static DocumentConverterFactory instance = new DocumentConverterFactory();

	private DocumentConverterFactory() {
		initDefault();
	}

	/**
	 * 设置  <a href="https://www.libreoffice.org/download/download/">LibreOffice</a> 软件的安装路径。
	 * 
	 * @param path
	 * @see #setConvertOpt(String, Object)
	 */
	@Bizlet("设置LibreOffice安装目录")
	public static void setLibreHome(String path) {
		opts.put(LibreDocumentConverter.OPT_OFFICE_HOME, path);
	}

	/**
	 * 文本转换时字符编码
	 * 
	 * @param charset 字符编码，默认使用UTF-8
	 * @see #setConvertOpt(String, Object)
	 */
	@Bizlet("设置转换时字符编码")
	public static void setCharset(String charset) {
		opts.put(AbstractTextConverter.OPT_CHARSET, charset);
	}

	/**
	 * 转换成图片是时生成的图片缩放比例
	 * 
	 * @param scaleRate
	 * @see #setConvertOpt(String, Object)
	 */
	@Bizlet("设置转成图片时的缩放比率")
	public static void setImageConverterScaleRate(float scaleRate) {
		opts.put(AbstractImageConverter.OPT_SCALE, scaleRate);
	}

	/**
	 * 设置转换参数，每个转换器中会有 <a href=""><code>OPT_***</code></a> 字段，使用前请参考
	 * @param key
	 * @param value
	 * @return
	 */
	@Bizlet("设置转换的附加参数")
	public static Map<String, Object> addConvertOpt(String key, Object value) {
		opts.put(key, value);
		return opts;
	}

	/**
	 * 根据输入输出文件类型构建转换器实例
	 * 
	 * @param inputFile
	 * @param targetFile
	 * @param useDefault
	 * @return
	 * @throws DocumentConvertException
	 * @throws IOException 
	 */
	@Bizlet("获取转换器实例")
	public static IDocumentConverter getConverter(File inputFile, File targetFile, boolean useDefault)
			throws DocumentConvertException, IOException {
		String key1 = FilenameUtils.getExtension(inputFile.getName());
		String key2 = FilenameUtils.getExtension(targetFile.getName());
		Class<? extends AbstractDocumentConverter> clazz = null;
		if (useDefault) {
			clazz = instance.defaultRegistry.get(instance.new Node(key1, key2));
		} else {
			clazz = instance.userRegistry.get(instance.new Node(key1, key2));
		}

		AbstractDocumentConverter converter = null;
		try {
			if (clazz == null) {
				clazz = instance.userRegistry.get(instance.new Node(key1, key2));
			}
			if (clazz == null) {
				throw new DocumentConvertException(
						MessageFormat.format("[ERROR] Can not find converter for {0} to {1}.", key1, key2));
			}
			converter = clazz.newInstance();
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(opts);
			converter.setConvertOpts(map);
//			converter.convert(inputFile, targetFile);
		} catch (InstantiationException e) {
			DocumentConvertException dce = new DocumentConvertException(e.getMessage());
			dce.initCause(e);
			throw dce;
		} catch (IllegalAccessException e) {
			DocumentConvertException dce = new DocumentConvertException(e.getMessage());
			dce.initCause(e);
			throw dce;
		} finally {
			opts.clear();
		}
		return converter;
	}

	private void initDefault() {
		if (defaultRegistry.isEmpty()) {
			defaultRegistry.put(new Node("doc", "html"), Word2HTMLConverter.class);
			defaultRegistry.put(new Node("doc", "pdf"), LibreDocumentConverter.class);

			defaultRegistry.put(new Node("docx", "html"), WordX2HTMLConverter.class);
			defaultRegistry.put(new Node("docx", "pdf"), LibreDocumentConverter.class);

			defaultRegistry.put(new Node("ppt", "pdf"), LibreDocumentConverter.class);
			defaultRegistry.put(new Node("ppt", "png"), PPT2ImageConverter.class);

			defaultRegistry.put(new Node("pptx", "pdf"), LibreDocumentConverter.class);
			defaultRegistry.put(new Node("pptx", "png"), PPTX2ImageConverter.class);

			defaultRegistry.put(new Node("xls", "html"), Excel2HTMLConverter.class);
			defaultRegistry.put(new Node("xls", "pdf"), LibreDocumentConverter.class);

			defaultRegistry.put(new Node("xlsx", "html"), LibreDocumentConverter.class);
			defaultRegistry.put(new Node("xlsx", "pdf"), LibreDocumentConverter.class);

			defaultRegistry.put(new Node("pdf", "html"), PDF2HtmlConverter.class);
			defaultRegistry.put(new Node("pdf", "png"), PDF2ImageConverter.class);
			defaultRegistry.put(new Node("pdf", "jpg"), PDF2ImageConverter.class);

			defaultRegistry.put(new Node("xml", "html"), LibreDocumentConverter.class);
			defaultRegistry.put(new Node("properties", "html"), LibreDocumentConverter.class);
			defaultRegistry.put(new Node("txt", "html"), LibreDocumentConverter.class);

			defaultRegistry.put(new Node("png", "html"), Image2HTMLConverter.class);
			defaultRegistry.put(new Node("jpg", "html"), Image2HTMLConverter.class);
			defaultRegistry.put(new Node("gif", "html"), Image2HTMLConverter.class);
			defaultRegistry.put(new Node("jpeg", "html"), Image2HTMLConverter.class);
			defaultRegistry.put(new Node("bmp", "html"), Image2HTMLConverter.class);
			defaultRegistry.put(new Node("tiff", "html"), Image2HTMLConverter.class);
			defaultRegistry.put(new Node("tif", "html"), Image2HTMLConverter.class);
			defaultRegistry.put(new Node("emf", "html"), Image2HTMLConverter.class);
			defaultRegistry.put(new Node("wmf", "html"), Image2HTMLConverter.class);
		}
	}

	/**
	 * 注册自定义文档转换器
	 * 
	 * @param inputExtension 单个且明确的文件后缀，eg: <code>doc</code>
	 * @param targetExtension 支持的目标文件类型后缀
	 * @param clazz
	 */
	@Bizlet("注册自定义文档转换器")
	public static void registConverter(String inputExtension, String targetExtension,
			Class<? extends AbstractDocumentConverter> clazz) {
		instance.userRegistry.put(instance.new Node(inputExtension, targetExtension), clazz);
	}

	/**
	 * 移除转换器
	 * @param inputExtension
	 * @param targetExtension
	 */
	@Bizlet("移除转换器")
	public static void removeConverter(String inputExtension, String targetExtension) {
		Node key = instance.new Node(inputExtension, targetExtension);
		instance.defaultRegistry.remove(key);
	}

	class Node implements Map.Entry<String, String> {

		String key;

		String value;

		Node(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public String setValue(String newValue) {
			String oldValue = value;
			value = newValue;
			return oldValue;
		}

		public String toString() {
			return key + "-->" + value;
		}

		public int hashCode() {
			return key.hashCode() ^ value.hashCode();
		}

		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (o instanceof Map.Entry) {
				Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
				boolean flag = key.equalsIgnoreCase(e.getKey().toString());
				int position = value.indexOf(e.getValue().toString().toLowerCase());
				return (flag && position > -1);
			}
			return false;
		}
	}
}
