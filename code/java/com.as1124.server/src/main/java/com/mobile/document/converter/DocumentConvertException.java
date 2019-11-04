package com.mobile.document.converter;

/**
 * 文档转换异常类定义
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class DocumentConvertException extends Exception {

	private static final long serialVersionUID = 1L;

	public DocumentConvertException(String message) {
		super(message);
	}

	public DocumentConvertException(String message, Throwable cause) {
		super(message, cause);
	}

}
