package com.primeton.mobile.thirdparty.wechat.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.primeton.mobile.thirdparty.wechat.IWechatConstants;
import com.primeton.mobile.thirdparty.wechat.MessageOperations;
import com.primeton.mobile.thirdparty.wechat.events.SubscribeEvent;
import com.primeton.mobile.thirdparty.wechat.message.TextMessage;
import com.primeton.mobile.thirdparty.wechat.model.AbstractDataPackage;
import com.primeton.mobile.thirdparty.wechat.undo.IWechatMessageHandler;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * 微信企业号接入Filter, 只拦截<code>html,jsp</code>页面.<br/>
 * 如果请求不是来自微信直接让具体的请求地址做处理
 * 
 * @author huangjw(mailto:haungjw@primton.com)
 */
@WebFilter("/WXServerFilter")
public class WXServerFilter implements Filter {

	private FilterConfig filterConfig;
	
    public WXServerFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		this.filterConfig = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		String encoding = filterConfig.getInitParameter("encoding");
		String sysEncoding = System.getProperty("file.encoding");
		if(!sysEncoding.equals(encoding)){
			// 设置JVM字符集编码
			System.setProperty("file.encoding", encoding);
		}
		HttpServletRequest hRequest = (HttpServletRequest) request;
		HttpServletResponse hResponse = (HttpServletResponse) response;
		
		HttpSession session = hRequest.getSession();
		
		
		String path = hRequest.getRequestURI();
		String extension = path.substring(path.lastIndexOf('.'), path.length());
		if(extension.equals(".hml") || extension.equals(".html")){
			hResponse.setContentType("text/html; charset=UTF-8");
			hResponse.addHeader("Content-Type", "text/html; charset=UTF-8");
		}else{
			hRequest.setCharacterEncoding(encoding);
			hResponse.setCharacterEncoding(encoding);
		}
		
		try{
			String KEY_ALGORITHM = "AES";
			String content = "9CQ+6M7be34uO7gfh20sNxxHHn1k8jyQ91psS+hSlyo=";
			String encryptKey = "8Q6NyAsL6OZ0odaWwrPVzWYOs3mBugX7";
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), KEY_ALGORITHM);  
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");  
            cipher.init(Cipher.ENCRYPT_MODE, key);  
            byte[] encryptedData = cipher.doFinal(content.getBytes("UTF-8"));
            System.out.println(encryptedData);
        }catch (Exception e){
        	e.printStackTrace();
        }
		
		// 微信企业号加密签名
		String msg_signature = hRequest.getParameter("msg_signature");
//		System.out.println("msg_signature="+msg_signature);
		
		// 微信公众号加密签名
		String signature = request.getParameter("signature"); 
//		System.out.println("signature="+signature);
		
		// 时间戳  
		String timestamp = hRequest.getParameter("timestamp");
//		System.out.println("timestamp="+timestamp);
		
		// 随机数  
		String nonce = hRequest.getParameter("nonce");
//		System.out.println("nonce="+nonce);
		
		// 随机字符串 ,只有URL验证时才有
		String echostr = hRequest.getParameter("echostr");
		System.out.println("echostr="+echostr);
		  
		PrintWriter pr = hResponse.getWriter();
		try {
			if(StringUtils.isNotBlank(msg_signature) && StringUtils.isNotBlank(echostr)){
				//企业号接入验证
				WXBizMsgCrypt cryptTool = new WXBizMsgCrypt(IWechatConstants.CORP_TOKEN, IWechatConstants.ENCODING_AES_KEY, IWechatConstants.CORP_ID);
				echostr = cryptTool.VerifyURL(msg_signature, timestamp, nonce, echostr);
				pr.print(echostr);
				pr.flush();
			}else if(StringUtils.isNotBlank(signature) && StringUtils.isNotBlank(echostr)){
				//公众号接入验证
				pr.print(echostr);
				pr.flush();
			}else if(StringUtils.isNotBlank(msg_signature) || StringUtils.isNotBlank(signature)){
				//防止消息重排,先直接回复空串
				pr.print("");
				pr.flush();
				handelRequestText(hRequest, hResponse);
				return;
			} 
		} catch (AesException e) {
			e.printStackTrace();
		}
		
		chain.doFilter(hRequest, hResponse);
	}

	/**
	 * 解析请求的xml报文并处理
	 * 
	 * @param hRequest
	 * @throws IOException
	 */
	protected void handelRequestText(HttpServletRequest hRequest, HttpServletResponse hResponse) throws IOException{
		BufferedReader reader = hRequest.getReader();
		String line = "";
		String postData = "";
		while((line=reader.readLine())!=null){
			postData = postData + line;
		}
		reader.close();
		if(StringUtils.isNotBlank(postData)){
			AbstractDataPackage event = MessageOperations.dealReceivedMessage(postData);
			Class<?>[] handlers = IWechatMessageHandler.class.getDeclaredClasses();
			for(Class handler : handlers){
				if(handler.isInterface() == false){
					try {
						((IWechatMessageHandler)handler.newInstance()).dealMessage(event, hRequest, hResponse, postData);
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(event instanceof SubscribeEvent){
				TextMessage msg = new TextMessage();
				msg.setToUser(event.getFromUser());
				msg.setFromUser(event.getToUser());
				msg.setCreateTime(System.currentTimeMillis());
				msg.setContent("http://weixin.mobile.primeto.com/default/apps/download?openid="+msg.getToUser());
				PrintWriter writer = hResponse.getWriter();
				writer.write(msg.toSendText());
				writer.flush();
			}
		}
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig = fConfig;
	}
	
}
