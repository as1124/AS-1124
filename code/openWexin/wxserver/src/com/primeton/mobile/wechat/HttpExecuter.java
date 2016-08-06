package com.primeton.mobile.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.primeton.mobile.wechat.exception.WechatExceprion;

/**
 * 
 * 封装基础的HttpClient操作.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class HttpExecuter {
	
	//HUANG GET请求都默认使用的DefaultHttpClient
	
	/**
	 * GET请求
	 * @param uri 连接的uri地址
	 * @param parameters 请求参数
	 * @return 响应结果的字节流形式
	 */
	public static byte[] executeGet(String uri, List<NameValuePair> parameters) {
		return executeGet(uri, parameters, 0);
	}
	
	/**
	 * GET请求
	 * @param uri 连接的uri地址
	 * @param parameters 请求参数
	 * @param timeout 连接超时时间
	 * @return 响应结果的字节流形式
	 */
	public static byte[] executeGet(String uri, List<NameValuePair> parameters, int timeout){
		String url = uri + "?" + URLEncodedUtils.format(parameters, IWechatConstants.DEFAULT_CHARSET);
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet method = new HttpGet(url);
		if(timeout > 0){
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();
			method.setConfig(requestConfig);
		}
		byte[] datas = null;
		try {
			HttpResponse response = httpClient.execute(method);
			datas = EntityUtils.toByteArray(response.getEntity());
			method.releaseConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * GET请求, 直接返回响应对象让用户自己处理结果
	 * 
	 * @param uri 连接的uri地址
	 * @param parameters 请求参数
	 * @return HttpResponse
	 */
	public static HttpResponse executeGetAsStream(String uri, List<NameValuePair> parameters){
		return executeGetAsStream(uri, parameters, 0);
	}
	
	/**
	 * GET请求, 直接返回响应对象让用户自己处理结果
	 * 
	 * @param uri 连接的uri地址
	 * @param parameters 请求参数
	 * @param timeout 连接超时时间
	 * @return HttpResponse
	 */
	public static HttpResponse executeGetAsStream(String uri, List<NameValuePair> parameters, int timeout){
		String url = uri + "?" + URLEncodedUtils.format(parameters, IWechatConstants.DEFAULT_CHARSET);
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet method = new HttpGet(url);
		if(timeout > 0){
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();
			method.setConfig(requestConfig);
		}
		try {
			return httpClient.execute(method);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * GET请求, 结果默认以UTF-8编码
	 * @param uri 连接的uri地址
	 * @param parameters 请求参数
	 * @return String
	 */
	public static String executeGetAsString(String uri, List<NameValuePair> parameters){
		return executeGetAsString(uri, parameters, IWechatConstants.DEFAULT_CHARSET, 0);
	}

	/**
	 * GET请求
	 * @param uri 连接的uri地址
	 * @param parameters 请求参数
	 * @param timeout 连接超时时间
	 * @return String
	 */
	public static String executeGetAsString(String uri, List<NameValuePair> parameters, String charset, int timeout){
		byte[] datas = executeGet(uri, parameters, timeout);
		try {
			return new String(datas, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * POST 请求
	 * @param uri 请求的uri
	 * @param parameters 查询参数
	 * @param requestEntity POST请求报文体
	 * @return 响应结果，字节流形式
	 */
	public static byte[] executePost(String url, List<NameValuePair> parameters, HttpEntity requestEntity) {
		return executePost(url, parameters, requestEntity, 0);
	}
	
	/**
	 * POST请求
	 * @param uri 请求的uri
	 * @param parameters 查询参数
	 * @param requestEntity POST请求报文体
	 * @param timeout 请求超时时间
	 * @return byte[]
	 */
	public static byte[] executePost(String uri, List<NameValuePair> parameters, HttpEntity requestEntity, int timeout) {
		String url = uri + "?" + URLEncodedUtils.format(parameters, IWechatConstants.DEFAULT_CHARSET);
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost method = new HttpPost(url);
		if(timeout > 0){
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();
			method.setConfig(requestConfig);
		}
		method.setEntity(requestEntity);
		byte[] datas = null;
		try {
			HttpResponse response = httpClient.execute(method);
			datas = EntityUtils.toByteArray(response.getEntity());
			method.releaseConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
//	/**
//	 * POST请求
//	 * @param uri 请求的uri
//	 * @param parameters 查询参数
//	 * @param parts POST请求需要传递的数据
//	 * @return byte[]
//	 */
//	public static byte[] executePost(String uri, List<NameValuePair> parameters, Part[] parts) {
//		return executePost(uri, parameters, parts, 0);
//	}
//	
//	/**
//	 * POST请求
//	 * @param uri 请求的uri
//	 * @param parameters 查询参数
//	 * @param part POST请求需要传递的数据
//	 * @param timeout 连接超时时间
//	 * @return byte[]
//	 */
//	public static byte[] executePost(String uri, List<NameValuePair> parameters, Part[] parts, int timeout) {
//		HttpClient httpClient = new HttpClient();
//		if(timeout > 0)
//			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
//		PostMethod method = new PostMethod(url);
//		method.setQueryString(queryStr);
//		method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
//		byte[] datas = null;
//		try {
//			httpClient.executeMethod(method);
//			datas = method.getResponseBody();
//			method.releaseConnection();
//		} catch (HttpException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return datas;
//	}
	
	/**
	 * POST请求
	 * @param uri 请求的uri
	 * @param parameters 查询参数
	 * @param requestEntity POST请求需要传递的数据
	 * @return HttpResponse
	 */
	public static HttpResponse executePostAsStream(String uri, List<NameValuePair> parameters, HttpEntity requestEntity) {
		return executePostAsStream(uri, parameters, requestEntity, 0);
	}
	
	/**
	 * POST请求
	 * @param uri 请求的uri
	 * @param parameters 查询参数
	 * @param requestEntity POST请求需要传递的数据
	 * @param timeout 请求超时时间
	 * @return HttpResponse
	 */
	public static HttpResponse executePostAsStream(String uri, List<NameValuePair> parameters, HttpEntity requestEntity, int timeout) {
		String url = uri + "?" + URLEncodedUtils.format(parameters, IWechatConstants.DEFAULT_CHARSET);
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost method = new HttpPost(url);
		if(timeout > 0){
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();
			method.setConfig(requestConfig);
		}
		method.setEntity(requestEntity);
		try {
			return httpClient.execute(method);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * POST请求
	 * @param uri 请求的uri
	 * @param parameters 查询参数
	 * @param requestEntity POST请求需要传递的数据
	 * @return String
	 */
	public static String executePostAsString(String uri, List<NameValuePair> parameters, HttpEntity requestEntity) {
		return executePostAsString(uri, parameters, requestEntity, IWechatConstants.DEFAULT_CHARSET, 0);
	}

	/**
	 * POST请求
	 * @param uri 请求的uri
	 * @param parameters 查询参数
	 * @param requestEntity POST请求需要传递的数据
	 * @param timeout 请求超时时间
	 * @return String
	 */
	public static String executePostAsString(String uri, List<NameValuePair> parameters, HttpEntity requestEntity, String charset, int timeout) {
		byte[] datas = executePost(uri, parameters, requestEntity, timeout);
		try {
			return new String(datas, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	/**
//	 * POST请求
//	 * @param url 请求的url
//	 * @param queryStr 查询参数
//	 * @param parts POST请求需要传递的数据
//	 * @param timeout 请求超时时间
//	 * @return String
//	 */
//	public static String executePostAsString(String url, NameValuePair[] queryStr, Part[] parts, int timeout) {
//		byte datas[] = null;
//		datas = executePost(url, queryStr, parts, timeout);
//		try {
//			return new String(datas, IWechatConstants.DEFAULT_CHARSET);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//
//	/**
//	 * POST请求
//	 * @param url 请求的url
//	 * @param queryStr 查询参数
//	 * @param parts POST请求需要传递的数据
//	 * @return String
//	 */
//	public static String executePostAsString(String url, NameValuePair[] queryStr, Part[] parts) {
//		return executePostAsString(url, queryStr, parts, 0);
//	}
	
	public static void main(String[] args){
		//String url = "https://api.weixin.qq.com/cgi-bin/token";
		String appid = "wxc70c5d9aab4f6a2b";
		String appSecret = "f3ca23ccf76c301f2158862db65cfdad";
		try {
			AccessToken token = AccessToken.getToken(appid, appSecret);
			token.getAccess_token();
		} catch (WechatExceprion e) {
			e.printStackTrace();
		}
		
	}
}
