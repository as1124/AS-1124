package com.as1124.document.converter;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

/**
 * 文档转换抽象基类
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public abstract class AbstractDocumentConverter implements IDocumentConverter {

	private Map<String, ?> convertOpts = null;

	private File inputSource = null;

	private File targetFile = null;

	/**
	 * 文件转换开始之前的处理动作
	 * @param inputSource 输入文件
	 * @param targetFile 输出文件
	 * @param opts 转换可选配置项
	 * @throws DocumentConvertException
	 */
	protected void beforeConvert(File inputSource, File targetFile, Map<String, ?> opts)
			throws DocumentConvertException {
		if (!inputSource.exists()) {
			throw new DocumentConvertException(MessageFormat.format("[ERROR] The source file ({0}) does not exists!",
				inputSource.getAbsolutePath()));
		}

		if (opts != null) {
			this.convertOpts = opts;
		}

		String inputExtension = FilenameUtils.getExtension(inputSource.getName());
		String targetExtension = FilenameUtils.getExtension(targetFile.getName());
		if (!isSupported(inputExtension, targetExtension)) {
			throw new DocumentConvertException(MessageFormat.format("[ERROR] {0} can not convert {1} to {2}.",
				getClass().getName(), inputExtension, targetExtension));
		}

		this.targetFile = targetFile;
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
	}

	@Override
	public boolean convert(File inputSource, File targetFile) throws IOException, DocumentConvertException {
		boolean result = false;
		try {
			beforeConvert(inputSource, targetFile, getConvertOpts());
			result = doConvert(inputSource, targetFile, getConvertOpts());
		} finally {
			afterConver(inputSource, targetFile);
		}
		return result;
	}

	/**
	 * 执行文档转换操作
	 * 
	 * @param inputSource 输入源
	 * @param targetFile 输出源
	 * @param opts 文档转换额外的设置参数
	 * @return true--成功
	 * @throws IOException
	 * @throws DocumentConvertException
	 */
	protected abstract boolean doConvert(File inputSource, File targetFile, Map<String, ?> opts)
			throws IOException, DocumentConvertException;

	/**
	 * 文件转换完成后处理动作
	 * @param inputSource
	 * @param targetFile
	 */
	protected void afterConver(File inputSource, File targetFile) {
		// do nothing
	}

	/**
	 * 文档转换配置参数
	 * 
	 * @return Returns the convertOpts.
	 */
	public Map<String, ?> getConvertOpts() {
		return convertOpts;
	}

	/**
	 * 设置转换可选配置项
	 * 
	 * @param convertOpts 可选设置项, {@linkplain DocumentServiceConstants.OPT_***}
	 */
	public void setConvertOpts(Map<String, ?> convertOpts) {
		this.convertOpts = convertOpts;
	}

	/**
	 * 待转换源文件
	 * 
	 * @return Returns the inputSource.
	 */
	public File getInputSource() {
		return inputSource;
	}

	/**
	 * @param inputSource The inputSource to set.
	 */
	protected void setInputSource(File inputSource) {
		this.inputSource = inputSource;
	}

	/**
	 * 转换后输出文件
	 * 
	 * @return Returns the targetFile.
	 */
	public File getTargetFile() {
		return targetFile;
	}

	/**
	 * @param targetFile The targetFile to set.
	 */
	protected void setTargetFile(File targetFile) {
		this.targetFile = targetFile;
	}

}
