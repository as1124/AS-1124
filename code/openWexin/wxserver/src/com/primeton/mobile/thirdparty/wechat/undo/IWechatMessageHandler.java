package com.primeton.mobile.thirdparty.wechat.undo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.primeton.mobile.thirdparty.wechat.model.AbstractDataPackage;

/**
 * 
 * 微信请求处理, 不包含微信URL验证请求。
 * <li>基本消息回复处理，如文字消息，语音、图片消息等
 * <li>事件处理，菜单事件，地理位置上报，连WIFI事件等
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public interface IWechatMessageHandler {

	/**
	 * 微信公众号消息处理函数
	 * 
	 * @param msgModel 消息对应的JAVA模型
	 * @param request
	 * @param response
	 * @param xmlContent 消息内容原报文
	 */
	public void dealMessage(AbstractDataPackage msgModel, HttpServletRequest request, HttpServletResponse response,
			String xmlContent);
}
