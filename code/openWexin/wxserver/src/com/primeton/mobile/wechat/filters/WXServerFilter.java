package com.primeton.mobile.wechat.filters;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.primeton.mobile.wechat.IWechatConstants;
import com.primeton.mobile.wechat.MessageOperations;
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
		
		String path = hRequest.getRequestURI();
		System.out.println("requestURI="+path);
		String extension = path.substring(path.lastIndexOf('.'), path.length());
		if(extension.equals(".hml") || extension.equals(".html")){
			hResponse.setContentType("text/html; charset=UTF-8");
			hResponse.addHeader("Content-Type", "text/html; charset=UTF-8");
		}else{
			hRequest.setCharacterEncoding(encoding);
			hResponse.setCharacterEncoding(encoding);
		}
		
		// 微信企业号加密签名
		String msg_signature = hRequest.getParameter("msg_signature");
		System.out.println("msg_signature="+msg_signature);
		
		// 微信公众号加密签名
		String signature = request.getParameter("signature"); 
		System.out.println("signature="+signature);
		
		// 时间戳  
		String timestamp = hRequest.getParameter("timestamp");
		System.out.println("timestamp="+timestamp);
		
		// 随机数  
		String nonce = hRequest.getParameter("nonce");
		System.out.println("nonce="+nonce);
		
		// 随机字符串 ,只有URL验证时才有
		String echostr = hRequest.getParameter("echostr");
		System.out.println("echostr="+echostr);
		  
		PrintWriter pr = hResponse.getWriter();
		try {
			WXBizMsgCrypt cryptTool = new WXBizMsgCrypt(IWechatConstants.CORP_TOKEN, 
					IWechatConstants.ENCODING_AES_KEY, IWechatConstants.CORP_ID);
			if(StringUtils.isNotBlank(msg_signature) && StringUtils.isNotBlank(echostr)){
				//企业号接入验证
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
				handelRequestText(hRequest);
			} 
		} catch (AesException e) {
			e.printStackTrace();
		}

		Enumeration<String> headers = hRequest.getHeaderNames();
		while(headers.hasMoreElements()){
			String header = headers.nextElement();
			System.out.println(header+"="+hRequest.getHeader(header));
		}
		
		chain.doFilter(hRequest, hResponse);
	}

	/**
	 * 将xml文本解析成Document
	 * 
	 * @param xmlContent
	 * @return
	 */
	public Element parseDocument(String xmlContent){
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes()));
			Element root = document.getRootElement();
			return root;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析请求的xml报文
	 * 
	 * @param hRequest
	 * @return
	 * @throws IOException
	 */
	protected String handelRequestText(HttpServletRequest hRequest) throws IOException{
		BufferedReader reader = hRequest.getReader();
		String line = "";
		String postData = "";
		while((line=reader.readLine())!=null){
			postData = postData + line;
		}
		if(StringUtils.isNotBlank(postData)){
			MessageOperations.dealReceivedMessage(postData);
		}
		reader.close();
		return postData;
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig = fConfig;
	}
	
}
