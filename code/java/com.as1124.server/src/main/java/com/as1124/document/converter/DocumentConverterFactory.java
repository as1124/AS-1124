package com.as1124.document.converter;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import com.as1124.document.converter.impl.icepdf.PDF2HtmlConverter;
import com.as1124.document.converter.impl.icepdf.PDF2ImageConverter;
import com.as1124.document.converter.impl.libre.LibreDocumentConverter;
import com.as1124.document.converter.impl.poi.Excel2HTMLConverter;
import com.as1124.document.converter.impl.poi.Image2HTMLConverter;
import com.as1124.document.converter.impl.poi.PPT2ImageConverter;
import com.as1124.document.converter.impl.poi.PPTX2ImageConverter;
import com.as1124.document.converter.impl.poi.Word2HTMLConverter;
import com.as1124.document.converter.impl.poi.WordX2HTMLConverter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * 文档转换器工厂
 * 
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class DocumentConverterFactory {

	static {
		initDefault();
	}

	/**
	 * Comment for <code>defaultRegistry</code>：默认配置的文档转换器，全局通用
	 */
	static Table<String, String, Class<? extends AbstractDocumentConverter>> defaultRegistry = HashBasedTable.create();

	/**
	 * Comment for <code>userRegistry</code>：用户自定义文档转换器， 允许覆盖
	 */
	Table<String, String, Class<? extends AbstractDocumentConverter>> userRegistry = HashBasedTable.create();

	private Map<String, Object> opts = new HashMap<>();

	private DocumentConverterFactory() {
		// default constructor
	}

	/**
	 * 根据输入输出文件类型构建转换器实例
	 * 
	 * @param inputFile 待转换文件
	 * @param targetFile 输出文件
	 * @return
	 * @throws DocumentConvertException
	 * @throws IOException 
	 */
	public IDocumentConverter getConverter(File inputFile, File targetFile)
			throws DocumentConvertException, IOException {
		String key1 = FilenameUtils.getExtension(inputFile.getName());
		String key2 = FilenameUtils.getExtension(targetFile.getName());

		// 优先从Override库中选择转换器
		Class<? extends AbstractDocumentConverter> clazz = userRegistry.get(key1, key2);
		if (clazz == null) {
			clazz = defaultRegistry.get(key1, key2);
		}
		if (clazz == null) {
			throw new DocumentConvertException(
					MessageFormat.format("[ERROR] Can not find converter for {0} to {1}.", key1, key2));
		}

		AbstractDocumentConverter converter = null;
		try {
			converter = clazz.newInstance();
			converter.setConvertOpts(opts);
		} catch (InstantiationException | IllegalAccessException e) {
			DocumentConvertException dce = new DocumentConvertException(e.getMessage());
			dce.initCause(e);
			throw dce;
		}
		return converter;
	}

	/**
	 * 注册自定义文档转换器，如果存在则不覆盖
	 * 
	 * @param inputExtension 单个且明确的文件后缀，eg: <code>doc</code>
	 * @param targetExtension 支持的目标文件类型后缀
	 * @param clazz
	 */
	public void registConverter(String inputExtension, String targetExtension,
			Class<? extends AbstractDocumentConverter> clazz) {
		if (!defaultRegistry.contains(inputExtension, targetExtension)) {
			defaultRegistry.put(inputExtension, targetExtension, clazz);
		}
	}

	/**
	 * 移除自定义转换器
	 * @param inputExtension
	 * @param targetExtension
	 */
	public void removeConverter(String inputExtension, String targetExtension) {
		userRegistry.remove(inputExtension, targetExtension);
	}

	private static void initDefault() {
		if (defaultRegistry.isEmpty()) {
			defaultRegistry.put("doc", "html", Word2HTMLConverter.class);
			defaultRegistry.put("doc", "pdf", LibreDocumentConverter.class);

			defaultRegistry.put("docx", "html", WordX2HTMLConverter.class);
			defaultRegistry.put("docx", "pdf", LibreDocumentConverter.class);

			defaultRegistry.put("ppt", "pdf", LibreDocumentConverter.class);
			defaultRegistry.put("ppt", "png", PPT2ImageConverter.class);

			defaultRegistry.put("pptx", "pdf", LibreDocumentConverter.class);
			defaultRegistry.put("pptx", "png", PPTX2ImageConverter.class);

			defaultRegistry.put("xls", "html", Excel2HTMLConverter.class);
			defaultRegistry.put("xls", "pdf", LibreDocumentConverter.class);

			defaultRegistry.put("xlsx", "html", LibreDocumentConverter.class);
			defaultRegistry.put("xlsx", "pdf", LibreDocumentConverter.class);

			defaultRegistry.put("pdf", "html", PDF2HtmlConverter.class);
			defaultRegistry.put("pdf", "png", PDF2ImageConverter.class);
			defaultRegistry.put("pdf", "jpg", PDF2ImageConverter.class);

			defaultRegistry.put("xml", "html", LibreDocumentConverter.class);
			defaultRegistry.put("properties", "html", LibreDocumentConverter.class);
			defaultRegistry.put("txt", "html", LibreDocumentConverter.class);

			defaultRegistry.put("png", "html", Image2HTMLConverter.class);
			defaultRegistry.put("jpg", "html", Image2HTMLConverter.class);
			defaultRegistry.put("gif", "html", Image2HTMLConverter.class);
			defaultRegistry.put("jpeg", "html", Image2HTMLConverter.class);
			defaultRegistry.put("bmp", "html", Image2HTMLConverter.class);
			defaultRegistry.put("tiff", "html", Image2HTMLConverter.class);
			defaultRegistry.put("tif", "html", Image2HTMLConverter.class);
			defaultRegistry.put("emf", "html", Image2HTMLConverter.class);
			defaultRegistry.put("wmf", "html", Image2HTMLConverter.class);
		}
	}

	public class DocumentCoverterBuilder {

		private DocumentConverterFactory converterFactory;

		public DocumentCoverterBuilder() {
			converterFactory = new DocumentConverterFactory();
		}

		/**
		 * 设置  <a href="https://www.libreoffice.org/download/download/">LibreOffice</a> 软件的安装路径。
		 * @param libreHome
		 * @return
		 */
		public DocumentCoverterBuilder setLibreHome(String libreHome) {
			opts.put(LibreDocumentConverter.OPT_OFFICE_HOME, libreHome);
			return this;
		}

		/**
		 * 设置生成文件的字符编码
		 * @param charset
		 * @return
		 */
		public DocumentCoverterBuilder setCharset(String charset) {
			opts.put(AbstractTextConverter.OPT_CHARSET, charset);
			return this;
		}

		/**
		 * 转换成图片是时生成的图片缩放比例
		 * @see #setConvertOpt(String, Object)
		 * @param scaleRate
		 * @return
		 */
		public DocumentCoverterBuilder setImageConverterScaleRate(float scaleRate) {
			opts.put(AbstractImageConverter.OPT_SCALE, scaleRate);
			return this;
		}

		/**
		 * 设置转换参数，每个转换器中会有 <code>OPT_***</code> 字段，使用前请参考
		 * @param key
		 * @param value
		 * @return
		 */
		public DocumentCoverterBuilder addConvertOpt(String key, Object value) {
			opts.put(key, value);
			return this;
		}

		/**
		 * 覆盖已有的文档转换器
		 * 
		 * @param inputFileExt
		 * @param outputFileExt
		 * @param clazz
		 * @return
		 */
		public DocumentCoverterBuilder overrideConverter(String inputFileExt, String outputFileExt,
				Class<? extends AbstractDocumentConverter> clazz) {
			userRegistry.put(inputFileExt, outputFileExt, clazz);
			return this;
		}

		public DocumentConverterFactory build() {
			return converterFactory;
		}
	}

}
