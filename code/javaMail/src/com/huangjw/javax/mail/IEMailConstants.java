package com.huangjw.javax.mail;

/**
 * 常量定义
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 * 
 */
public interface IEMailConstants {

	public static final String PROTOCOL_SMTP = "smtp";

	public static final String PROTOCOL_POP3 = "pop3";

	public static final String PROTOCOL_IMAP = "imap";

	public static final int SMTP_PORT = 25;

	public static final int SMTP_SSL_PORT = 465;

	public static final int POP3_PORT = 110;

	public static final int POP3_SSL_PORT = 995;

	public static final int IMAP_PORT = 143;

	public static final int IMAP_SSL_PORT = 993;

	public static final String KEY_HOST = "HOST";

	public static final String KEY_DOMAIN = "DOMAIN";

	/*******************收件协议配置关键字***********************/
	public static final String KEY_IN_PROTOCAL = "IN_PROTOCOL";
	public static final String KEY_IN_PORT = "IN_PORT";
	public static final String KEY_IN_HOST = "IN_HOST";
	public static final String KEY_IN_SSL = "IN_SSL";
	
	/*******************发件协议配置关键字************************/
	public static final String KEY_OUT_PROTOCAL = "OUT_PROTOCOL";
	public static final String KEY_OUT_PORT = "OUT_PORT";
	public static final String KEY_OUT_HOST = "OUT_HOST";
	public static final String KEY_OUT_SSL = "OUT_SSL";

}
