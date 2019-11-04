/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年8月16日
 *******************************************************************************/

package com.mobile.document.converter;

/**
 * 目标文件类型为文本(eg: html)转换器标志接口
 *
 * @author huangjw (mailto:huangjw@primeton.com)
 */
public abstract class AbstractTextConverter extends AbstractDocumentConverter {

	/**
	 * Comment for <code>OPT_CHARSET</code>：<br>
	 * 设置文档转换过后的字符编码，如该字段没有设置则默认为 <code>UTF-8</code>
	 * 
	 */
	public static final String OPT_CHARSET = "CHARSET";

}
