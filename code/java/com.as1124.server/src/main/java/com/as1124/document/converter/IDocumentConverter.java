package com.as1124.document.converter;

import java.io.File;
import java.io.IOException;

/**
 * 文件转换服务接口
 * 
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public interface IDocumentConverter {

	/**
	 * 转换文档
	 * 
	 * @param inputSource 源文件
	 * @param targetFile 目标文件
	 * @return 是否成功
	 * @throws IOException
	 * @throws DocumentConvertException
	 */
	public boolean convert(File inputSource, File targetFile) throws IOException, DocumentConvertException;

	/**
	 * 是否支持转换为目标类型文件
	 * 
	 * @param inputExtension 源文件扩展名
	 * @param targetExtension 目标文件扩展名
	 * @return <code>true</code> - 支持转换
	 */
	public boolean isSupported(String inputExtension, String targetExtension);
}
