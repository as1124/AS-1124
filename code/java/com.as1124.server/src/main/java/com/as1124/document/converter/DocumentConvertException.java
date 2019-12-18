package com.as1124.document.converter;

/**
 * 文档转换异常类定义
 * 
 * @author As-1124(mailto:as1124huang@gmail.com)
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
