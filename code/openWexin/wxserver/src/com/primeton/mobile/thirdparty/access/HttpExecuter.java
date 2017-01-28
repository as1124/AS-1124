package com.primeton.mobile.thirdparty.access;

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

import com.primeton.mobile.thirdparty.wechat.IWechatConstants;

/**
 * 
 * 封装基础的HttpClient操作.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class HttpExecuter {
	
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
	
}
