package com.primeton.mobile.thirdparty.access;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

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
		String url = uri + "?" + URLEncodedUtils.format(parameters, getCharset(null));
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet method = new HttpGet(url);
		if(timeout > 0){
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();
			method.setConfig(requestConfig);
		}
		byte[] datas = null;
		try {
			HttpResponse response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			datas = EntityUtils.toByteArray(entity);
			EntityUtils.consume(entity);
			method.releaseConnection();
			httpClient.close();
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
		String url = uri + "?" + URLEncodedUtils.format(parameters, getCharset(null));
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
		return executeGetAsString(uri, parameters, getCharset(null), 0);
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
			return new String(datas, getCharset(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param httpClient
	 * @param url 完整URL
	 * @param reffer
	 * @param cookie
	 * @param socketTimeout
	 * @param connectTimeout
	 * @param charset
	 * @param closeHttpClient
	 * @return
	 */
	public static String executeGetAsString(CloseableHttpClient httpClient, String url, String reffer, 
			String cookie, int socketTimeout, int connectTimeout, String charset, boolean closeHttpClient){
		CloseableHttpResponse httpResponse = null;
		if (httpClient == null) {
			httpClient = createCustomHttpClient(socketTimeout, connectTimeout);
		}
		HttpGet getMethod = new HttpGet(url);
		if (cookie!=null && cookie.equals("")==false) {
			getMethod.setHeader("Cookie", cookie);
		}
		if (reffer!=null && reffer.equals("")==false) {
			getMethod.setHeader("Reffer", reffer);
		}
		
		String result = null;
		try {
			httpResponse = httpClient.execute(getMethod);
			result = getResult(httpResponse, getCharset(charset));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
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
	 * @param timeout 连接超时时间
	 * @return byte[]
	 */
	public static byte[] executePost(String uri, List<NameValuePair> parameters, HttpEntity requestEntity, int timeout) {
		String url = uri + "?" + URLEncodedUtils.format(parameters, getCharset(null));
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost method = new HttpPost(url);
		if(timeout > 0){
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();
			method.setConfig(requestConfig);
		}
		method.setEntity(requestEntity);
		byte[] datas = null;
		try {
			HttpResponse response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			datas = EntityUtils.toByteArray(entity);
			EntityUtils.consume(entity);
			method.releaseConnection();
			httpClient.close();
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
	 * @param timeout 连接超时时间
	 * @return HttpResponse
	 */
	public static HttpResponse executePostAsStream(String uri, List<NameValuePair> parameters, HttpEntity requestEntity, int timeout) {
		String url = uri + "?" + URLEncodedUtils.format(parameters, getCharset(null));
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
		return executePostAsString(uri, parameters, requestEntity, getCharset(null), 0);
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
			return new String(datas, getCharset(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param httpClient
	 * @param url 完整URL
	 * @param entity
	 * @param reffer
	 * @param cookie
	 * @param socketTimeout socket超时时间
	 * @param connectTimeout 连接超时时间
	 * @param charset
	 * @param closeHttpClient
	 * @return
	 */
	public static String executePostAsString(CloseableHttpClient httpClient, String url, HttpEntity entity, 
			String reffer, String cookie, int socketTimeout, int connectTimeout, String charset, boolean closeHttpClient) {
		CloseableHttpResponse httpResponse = null;
		if (httpClient == null) {
			httpClient = createCustomHttpClient(socketTimeout, connectTimeout);
		}
		HttpPost postMethod = new HttpPost(url);
		if (cookie!=null && cookie.equals("")==false) {
			postMethod.setHeader("Cookie", cookie);
		}
		if (reffer!=null && reffer.equals("")==false) {
			postMethod.setHeader("Reffer", reffer);
		}
		if (entity != null) {
			postMethod.setEntity(entity);
		}
		String result = null;
		try {
			httpResponse = httpClient.execute(postMethod);
			result = getResult(httpResponse, getCharset(charset));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 创建自定义的HttpClient
	 * @param socketTimeout socket超时时间
	 * @param connectTimeout 连接超时时间
	 * @return
	 */
	public static CloseableHttpClient createCustomHttpClient(int socketTimeout, int connectTimeout) {
		Builder builder = RequestConfig.custom();

		// 设置连接超时时间，单位毫秒
		builder.setConnectTimeout(connectTimeout);

		// 设置从connect manager获取的connection超时时间，毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的
		builder.setConnectionRequestTimeout(connectTimeout);

		// 请求获取数据的超时时间，单位毫秒
		builder.setSocketTimeout(socketTimeout);
		Collection<String> authSchemes = Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST);
		RequestConfig defaultRequestConfig = builder.setCookieSpec(CookieSpecs.STANDARD)
				.setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(authSchemes)
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

		// 开启HTTPS支持
		SSLConnectionSocketFactory socketFactory = getSSLConnectionFactory();

		// 创建可用的Scheme
		Registry<?> socketFactoryRegistry = RegistryBuilder.create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", socketFactory).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				(Registry<ConnectionSocketFactory>) socketFactoryRegistry);
		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		CloseableHttpClient httpClient = httpClientBuilder
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(defaultRequestConfig).build();
		return httpClient;
	}

	private static SSLConnectionSocketFactory getSSLConnectionFactory() {
		SSLConnectionSocketFactory socketFactory = null;
		// https网站一般情况下使用了安全系数较低的SHA-1签名，因此首先我们在调用SSL之前需要重写验证方法，取消检测SSL。
		TrustManager manager = new X509TrustManager() {

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				System.out.println("checkServerTrusted");
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				System.out.println("checkClientTrusted");
			}

		};
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { manager }, null);
			socketFactory = new SSLConnectionSocketFactory(context, new BrowserCompatHostnameVerifier());// NoopHostnameVerifier.INSTANCE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return socketFactory;
	}
	

	/**
	 * 文件上传
	 * @param httpClient
	 * @param url 请求URL
	 * @param localFilePath 待上传文件的本地路径
	 * @param partName 字段名称
	 * @return
	 */
	public static String executeUploadFile(CloseableHttpClient httpClient, String url, String localFilePath, String partName){
		return executeUploadFile(httpClient, url, localFilePath, partName, null);
	}

	/**
	 *  文件上传
	 * @param httpClient
	 * @param url 请求URL
	 * @param localFilePath 待上传文件的本地路径
	 * @param partName 字段名称
	 * @param contentParts 上传文件时需要的附属表单
	 * @return
	 */
	public static String executeUploadFile(CloseableHttpClient httpClient, String url, String localFilePath, String partName, Map<String, ContentBody> contentParts){
		CloseableHttpResponse httpResponse = null;
		if (httpClient == null) {
			httpClient = createCustomHttpClient(60000, 0);
		}

		File localFile = new File(localFilePath);

		// 以浏览器兼容模式运行，防止文件名乱码
		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addBinaryBody(partName, localFile, ContentType.MULTIPART_FORM_DATA, localFile.getName());
		if(contentParts!=null && contentParts.size()>0) {
			Iterator<String> it = contentParts.keySet().iterator();
			while(it.hasNext()){
				String partid = it.next();
				entityBuilder.addPart(partid, contentParts.get(partid));
			}
		}
		HttpEntity requestEntity = entityBuilder.build();
				
		// uploadFile对应服务端类的同名属性<File类型>
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(requestEntity);
		String result = null;
		
		try {
			httpResponse = httpClient.execute(httpPost);
			result = getResult(httpResponse, getCharset(null));
			httpPost.releaseConnection();
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static String getResult(CloseableHttpResponse httpResponse, String charset) throws IOException {
		String result = null;
		if (httpResponse == null) {
			return result;
		}
		HttpEntity entity = httpResponse.getEntity();
		if (entity == null) {
			return result;
		}
		result = EntityUtils.toString(entity, charset);
		EntityUtils.consume(entity);
		return result;

	}

	public static String getCharset(String charset) {
		if(charset==null || charset.trim().equals("")){
			return "UTF-8";
		} else return charset;
	}
	
}
