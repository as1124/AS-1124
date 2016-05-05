package com.primeton.mobile.wechat;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

/**
 * 
 * 封装基础Http操作.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class HttpExecuter {
	
	/**
	 * GET请求
	 * @param url 连接的url地址
	 * @param queryStr 查询参数
	 * @return 响应结果的字节流形式
	 */
	public static byte[] executeGet(String url, NameValuePair[] queryStr) {
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.setQueryString(queryStr);
		byte[] datas = null;
		try {
			httpClient.executeMethod(method);
			datas = method.getResponseBody();
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * GET请求
	 * @param url 连接的url地址
	 * @param queryStr 查询参数
	 * @param timeout 连接超时时间
	 * @return 响应结果的字节流形式
	 */
	public static byte[] executeGet(String url, NameValuePair[] queryStr, int timeout){
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		GetMethod method = new GetMethod(url);
		method.setQueryString(queryStr);
		byte[] datas = null;
		try {
			httpClient.executeMethod(method);
			datas = method.getResponseBody();
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * GET请求
	 * @param url 连接的url地址
	 * @param queryStr 查询参数
	 * @return InputStream
	 */
	public static InputStream executeGetAsStream(String url, NameValuePair[] queryStr){
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.setQueryString(queryStr);
		InputStream inputStream = null;
		try {
			httpClient.executeMethod(method);
			inputStream = method.getResponseBodyAsStream();
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * GET请求
	 * @param url 连接的url地址
	 * @param queryStr 查询参数
	 * @param timeout 连接超时时间
	 * @return InputStream
	 */
	public static InputStream executeGetAsStream(String url, NameValuePair[] queryStr, int timeout){
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		GetMethod method = new GetMethod(url);
		method.setQueryString(queryStr);
		InputStream inputStream = null;
		try {
			httpClient.executeMethod(method);
			inputStream = method.getResponseBodyAsStream();
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * GET请求
	 * @param url 连接的url地址
	 * @param queryStr 查询参数
	 * @return String
	 */
	public static String executeGetAsString(String url, NameValuePair[] queryStr){
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.setQueryString(queryStr);
		String result = "";
		try {
			httpClient.executeMethod(method);
			byte[] datas = method.getResponseBody();
			method.releaseConnection();
			result = new String(datas, IWechatConstants.DEFAULT_CHARSET);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * GET请求
	 * @param url 连接的url地址
	 * @param queryStr 查询参数
	 * @param timeout 连接超时时间
	 * @return String
	 */
	public static String executeGetAsString(String url, NameValuePair[] queryStr, int timeout){
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		GetMethod method = new GetMethod(url);
		method.setQueryString(queryStr);
		String result = "";
		try {
			httpClient.executeMethod(method);
			byte[] datas = method.getResponseBody();
			method.releaseConnection();
			result = new String(datas, IWechatConstants.DEFAULT_CHARSET);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * POST 请求
	 * @param url 请求的url
	 * @param queryStr 查询参数
	 * @param requestEntity POST请求需要传递的请求数据对象
	 * @return 响应结果，字节流形式
	 */
	public static byte[] executePost(String url, NameValuePair[] queryStr, RequestEntity requestEntity) {
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.setQueryString(queryStr);
		method.setRequestEntity(requestEntity);
		byte[] datas = null;
		try {
			httpClient.executeMethod(method);
			datas = method.getResponseBody();
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * POST请求
	 * @param url 请求的url
	 * @param queryStr 查询参数
	 * @param requestEntity POST请求需要传递的参数
	 * @param timeout 请求超时时间
	 * @return byte[]
	 */
	public static byte[] executePost(String url, NameValuePair[] queryStr, RequestEntity requestEntity, int timeout) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		PostMethod method = new PostMethod(url);
		method.setQueryString(queryStr);
		method.setRequestEntity(requestEntity);
		byte[] datas = null;
		try {
			httpClient.executeMethod(method);
			datas = method.getResponseBody();
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * POST请求
	 * @param url 请求的url
	 * @param queryStr 查询参数
	 * @param parts POST请求需要传递的数据
	 * @return byte[]
	 */
	public static byte[] executePost(String url, NameValuePair[] queryStr, Part[] parts) {
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.setQueryString(queryStr);
		method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
		byte[] datas = null;
		try {
			httpClient.executeMethod(method);
			datas = method.getResponseBody();
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * POST请求
	 * @param url 请求的url
	 * @param queryStr 查询参数
	 * @param part POST请求需要传递的数据
	 * @param timeout 连接超时时间
	 * @return byte[]
	 */
	public static byte[] executePost(String url, NameValuePair[] queryStr, Part[] parts, int timeout) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		PostMethod method = new PostMethod(url);
		method.setQueryString(queryStr);
		method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
		byte[] datas = null;
		try {
			httpClient.executeMethod(method);
			datas = method.getResponseBody();
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * POST请求
	 * @param url 请求的url
	 * @param queryStr 查询参数
	 * @param requestEntity POST请求需要传递的数据
	 * @return InputStream
	 */
	public static InputStream executePostAsStream(String url, NameValuePair[] queryStr, RequestEntity requestEntity) {
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.setQueryString(queryStr);
		method.setRequestEntity(requestEntity);
		InputStream inputStream = null;
		try {
			httpClient.executeMethod(method);
			inputStream = method.getResponseBodyAsStream();
			//ATTENTION 释放链接后还能取到inputStream内容么？
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * POST请求
	 * @param url 请求的url
	 * @param queryStr 查询参数
	 * @param requestEntity POST请求需要传递的数据
	 * @param timeou 请求超时时间
	 * @return InputStream
	 */
	public static InputStream executePostAsStream(String url, NameValuePair[] queryStr, RequestEntity requestEntity, int timeout) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		PostMethod method = new PostMethod(url);
		method.setQueryString(queryStr);
		method.setRequestEntity(requestEntity);
		InputStream inputStream = null;
		try {
			httpClient.executeMethod(method);
			inputStream = method.getResponseBodyAsStream();
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * POST请求
	 * @param url 请求的url
	 * @param queryStr 查询参数
	 * @param requestEntity POST请求需要传递的数据
	 * @return String
	 */
	public static String executePostAsString(String url, NameValuePair[] queryStr, RequestEntity requestEntity) {
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.setQueryString(queryStr);
		method.setRequestEntity(requestEntity);
		String result = "";
		try {
			httpClient.executeMethod(method);
			result = new String(method.getResponseBody(), IWechatConstants.DEFAULT_CHARSET);
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * POST请求
	 * @param url 请求的url
	 * @param queryStr 查询参数
	 * @param requestEntity POST请求需要传递的数据
	 * @param timeout 请求超时时间
	 * @return String
	 */
	public static String executePostAsString(String url, NameValuePair[] queryStr, RequestEntity requestEntity, int timeout) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		PostMethod method = new PostMethod(url);
		method.setQueryString(queryStr);
		method.setRequestEntity(requestEntity);
		String result = "";
		try {
			httpClient.executeMethod(method);
			result = new String(method.getResponseBody(), IWechatConstants.DEFAULT_CHARSET);
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * POST请求
	 * @param url 请求的url
	 * @param queryStr 查询参数
	 * @param parts POST请求需要传递的数据
	 * @param timeout 请求超时时间
	 * @return String
	 */
	public static String executePostAsString(String url, NameValuePair[] queryStr, Part[] parts, int timeout) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
		PostMethod method = new PostMethod(url);
		method.setQueryString(queryStr);
		method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
		String result = "";
		try {
			httpClient.executeMethod(method);
			result = new String(method.getResponseBody(), IWechatConstants.DEFAULT_CHARSET);
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	

	/**
	 * POST请求
	 * @param url 请求的url
	 * @param queryStr 查询参数
	 * @param parts POST请求需要传递的数据
	 * @return String
	 */
	public static String executePostAsString(String url, NameValuePair[] queryStr, Part[] parts) {
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.setQueryString(queryStr);
		method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
		String result = "";
		try {
			httpClient.executeMethod(method);
			result = new String(method.getResponseBody(), IWechatConstants.DEFAULT_CHARSET);
			method.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
