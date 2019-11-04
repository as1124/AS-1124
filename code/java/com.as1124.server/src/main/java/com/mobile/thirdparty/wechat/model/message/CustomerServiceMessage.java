package com.mobile.thirdparty.wechat.model.message;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;

/**
 * 将消息转发到客服时用。
 * <P>如果公众号处于开发模式，普通微信用户向公众号发消息时，微信服务器会先将消息POST到开发者填写的url上，
 * 如果希望将消息转发到客服系统，则需要开发者在响应包中返回MsgType为transfer_customer_service的消息，
 * 微信服务器收到响应后会把当次发送的消息转发至客服系统。您也可以在返回transfer_customer_service消息时，
 * 在XML中附上TransInfo信息指定分配给某个客服帐号。</P>
 * @author huangjw(huangjw@primeton.com)
 *
 */
public class CustomerServiceMessage extends AbstractMessage {

	/**
	 * 指定会话接入的客服账号
	 */
	protected String kfAccount;
	
	public CustomerServiceMessage() {
		super("transfer_customer_service");
	}

	public String toSendText() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
				+"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
		 +"<CreateTime>"+this.getSentTime()+"</CreateTime>"
		 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>";
		if(StringUtils.isNotBlank(this.kfAccount)){
			result = result + "<TransInfo><KfAccount><![CDATA["+this.kfAccount+"]]></KfAccount>";
		}
		result = result + "</xml>";
		return result;
	}

	public Document decodeFromXML(String xmlContent) {
		return null;
	}

	/**
	 * @return the {@link #kfAccount}
	 */
	public String getKfAccount() {
		return kfAccount;
	}

	/**
	 * @param kfAccount the {@link #kfAccount} to set
	 */
	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}
	
}
