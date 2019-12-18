package com.as1124.document.converter;

/**
 * 目标文件类型为文本(eg: html)转换器标志接口
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public abstract class AbstractTextConverter extends AbstractDocumentConverter {

	/**
	 * Comment for <code>OPT_CHARSET</code>：<br>
	 * 设置文档转换过后的字符编码，如该字段没有设置则默认为 <code>UTF-8</code>
	 * 
	 */
	public static final String OPT_CHARSET = "CHARSET";

}
