package com.qq.weixin.mp.aes;

/**
 * 代码参阅 <a href="http://mp.weixin.qq.com/wiki">微信公众平台/消息加解密说明</a>
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class AesException extends Exception {
	
	private static final long serialVersionUID = -4378426861236691027L;
	
	public final static int OK = 0;
	
	public final static int ValidateSignatureError = -40001;
	
	public final static int ParseXmlError = -40002;
	
	public final static int ComputeSignatureError = -40003;
	
	public final static int IllegalAesKey = -40004;
	
	public final static int ValidateAppidError = -40005;
	
	public final static int EncryptAESError = -40006;
	
	public final static int DecryptAESError = -40007;
	
	public final static int IllegalBuffer = -40008;
	
	public final static int EncodeBase64Error = -40009;
	
	public final static int DecodeBase64Error = -40010;
	
	private int code;

	private static String getMessage(int code) {
		switch (code) {
		case ValidateSignatureError:
			return "Signature valid error";
		case ParseXmlError:
			return "The xml-content-text parse error";
		case ComputeSignatureError:
			return "SHA-1 signature generate failed";
		case IllegalAesKey:
			return "SymmetricKey illegal";
		case ValidateAppidError:
			return "appid not match";
		case EncryptAESError:
			return "aes encrypt error";
		case DecryptAESError:
			return "aes decrypt error";
		case IllegalBuffer:
			return "decrypt buffer has error";
		case EncodeBase64Error:
			return "base64 encrpt error";
		case DecodeBase64Error:
			return "base64 decrypt error";
		default:
			return null; // cannot be
		}
	}

	public int getCode() {
		return code;
	}

	protected AesException(int code) {
		super(getMessage(code));
		this.code = code;
	}
}
