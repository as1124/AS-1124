package com.as1124.document.converter;

import java.nio.charset.StandardCharsets;

/**
 * 组件常量定义
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public final class DocumentServiceConstants {

	/**
	 * Comment for <code>CHARSET_UTF8</code>：默认字符集编码
	 */
	public static final String CHARSET_UTF8 = StandardCharsets.UTF_8.name();

	/**
	 * Comment for <code>SEGMENT_SEPARATOR</code>：多图片时命名分隔符
	 */
	public static final String SEGMENT_SEPARATOR = "_";

	private DocumentServiceConstants() {
		// default constructor
	}
}
