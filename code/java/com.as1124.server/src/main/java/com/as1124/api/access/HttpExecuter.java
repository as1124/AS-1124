package com.as1124.api.access;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
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
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
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

import com.as1124.common.util.LoggerUtil;

/**
 * 
 * 封装基础的HttpClient操作.
 * 
 * <pre>
 * eg:GET请求调用示例
 * <code>
 * String url = "https://api.weixin.qq.com/cgi-bin/token";
 * List&lt;NameValuePair> parameters = new ArrayList&lt;NameValuePair>();
 * parameters.add(new BasicNameValuePair("appid", appid));
 * parameters.add(new BasicNameValuePair("secret", appSecret));
 * parameters.add(new BasicNameValuePair("grant_type", "client_credential"));
 * {@link HttpExecuter}.{@link #executeGetAsString(url, parameters)}
 * </code>
 * eg:POST请求调用示例
 * <code>String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
 * List&lt;NameValuePair> queryStr = new ArrayList&lt;NameValuePair>();
 * queryStr.add(new BasicNameValuePair("access_token", access_token);
 * String postData = "{"filter":{"is_to_all":false, "tag_id":2}}";
 * StringEntity requestEntity = new StringEntity(postData, ContentType.create("text/json", "UTF-8"));
 * {@link HttpExecuter}.{@link #executePostAsString(url, queryStr, requestEntity)};
 * </code>
 * </pre>
 * 
 * @author As-1124(mailto:as1124huang@gmail.com)
 *
 */
public class HttpExecuter {

	static Logger logger = LoggerUtil.getLogger(HttpExecuter.class);

	private HttpExecuter() {
	}

	/**
	 * GET请求
	 * 
	 * @param uri 请求地址
	 * @param parameters 请求参数
	 * @return 响应返回的字节流数据
	 */
	public static byte[] executeGet(String uri, List<NameValuePair> parameters) {
		return executeGet(uri, parameters, 0);
	}

	/**
	 * GET请求
	 * 
	 * @param uri 请求地址
	 * @param parameters 请求参数
	 * @param timeout 连接超时时间
	 * @return 响应返回的字节流数据
	 */
	public static byte[] executeGet(String uri, List<NameValuePair> parameters, int timeout) {
		String requestURL = uri;
		if (parameters != null && !parameters.isEmpty()) {
			requestURL = uri + "?" + URLEncodedUtils.format(parameters, getCharset(null));
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet method = new HttpGet(requestURL);
		if (timeout > 0) {
			Builder builder = RequestConfig.custom();
			RequestConfig requestConfig = builder.setConnectTimeout(timeout).setSocketTimeout(timeout).build();
			method.setConfig(requestConfig);
		}
		byte[] datas = new byte[0];
		try {
			HttpResponse response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			datas = EntityUtils.toByteArray(entity);
			EntityUtils.consume(entity);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "请求微信接口失败：URL = " + uri, e);
		} finally {
			method.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}
		return datas;
	}

	/**
	 * GET请求，返回原始响应对象，用户须自行处理数据
	 * 
	 * @param uri 请求地址
	 * @param parameters 请求参数
	 * @return HttpResponse
	 * @see HttpResponse#getEntity()
	 * @see HttpEntity#getContent()
	 */
	public static HttpResponse executeGetAsStream(String uri, List<NameValuePair> parameters) {
		return executeGetAsStream(uri, parameters, 0);
	}

	/**
	 * GET请求，返回原始响应对象，用户须自行处理数据
	 * 
	 * @param uri 请求地址
	 * @param parameters 请求参数
	 * @param timeout 连接超时时间
	 * @return HttpResponse
	 * @see HttpResponse#getEntity()
	 * @see HttpEntity#getContent()
	 */
	public static HttpResponse executeGetAsStream(String uri, List<NameValuePair> parameters, int timeout) {
		String requestURL = uri;
		if (parameters != null && !parameters.isEmpty()) {
			requestURL = uri + "?" + URLEncodedUtils.format(parameters, getCharset(null));
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet method = new HttpGet(requestURL);
		if (timeout > 0) {
			Builder builder = RequestConfig.custom();
			RequestConfig requestConfig = builder.setConnectTimeout(timeout).setSocketTimeout(timeout).build();
			method.setConfig(requestConfig);
		}
		try {
			return httpClient.execute(method);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "请求微信接口失败：URL = " + uri, e);
		}
		return null;
	}

	/**
	 * GET请求, 结果默认返回以UTF-8编码的字符数据
	 * 
	 * @param uri 请求地址
	 * @param parameters 请求参数
	 * @return String
	 */
	public static String executeGetAsString(String uri, List<NameValuePair> parameters) {
		return executeGetAsString(uri, parameters, getCharset(null), 0);
	}

	/**
	 * GET请求
	 * 
	 * @param uri 请求地址
	 * @param parameters 请求参数
	 * @param charset 返回的结果数据的编码
	 * @param timeout 连接超时时间
	 * @return String
	 */
	public static String executeGetAsString(String uri, List<NameValuePair> parameters, String charset, int timeout) {
		byte[] datas = executeGet(uri, parameters, timeout);
		Charset namedCharset = Charset.forName(getCharset(charset));
		return new String(datas, namedCharset);
	}

	/**
	 * GET请求
	 * 
	 * @param httpClient
	 * @param url 完整URL，包含请求参数。如
	 *            <code>https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET</code>
	 * @param reffer
	 * @param cookie
	 * @param timeout 超时时间
	 * @param charset 返回结果的字符集编码
	 * @return
	 */
	public static String executeGetAsString(CloseableHttpClient httpClient, String url, String reffer, String cookie,
			int timeout, String charset) {
		CloseableHttpClient httpClientRef = httpClient;
		if (httpClientRef == null) {
			httpClientRef = createCustomHttpClient(timeout, timeout);
		}
		HttpGet getMethod = new HttpGet(url);
		if (StringUtils.isNotBlank(cookie)) {
			getMethod.setHeader("Cookie", cookie);
		}
		if (StringUtils.isNotBlank(reffer)) {
			getMethod.setHeader("Reffer", reffer);
		}

		String result = "";
		try (CloseableHttpResponse httpResponse = httpClientRef.execute(getMethod)) {
			if (httpResponse == null || httpResponse.getEntity() == null) {
				return result;
			}
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity, getCharset(charset));
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "请求微信接口失败：URL = " + url, e);
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				// do not care
			}
		}
		return result;
	}

	/**
	 * POST 请求
	 * 
	 * @param uri 请求uri地址
	 * @param parameters 查询参数
	 * @param requestEntity POST请求报文体
	 * @return 响应结果，字节流形式
	 */
	public static byte[] executePost(String uri, List<NameValuePair> parameters, HttpEntity requestEntity) {
		return executePost(uri, parameters, requestEntity, 0);
	}

	/**
	 * POST 请求
	 * 
	 * @param uri 请求的uri地址
	 * @param parameters 查询参数
	 * @param requestEntity POST请求报文体
	 * @param timeout 连接超时时间
	 * @return byte[]
	 */
	public static byte[] executePost(String uri, List<NameValuePair> parameters, HttpEntity requestEntity,
			int timeout) {
		String requestURL = uri;
		if (parameters != null && !parameters.isEmpty())
			requestURL = uri + "?" + URLEncodedUtils.format(parameters, getCharset(null));
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost method = new HttpPost(requestURL);
		if (timeout > 0) {
			Builder builder = RequestConfig.custom();
			RequestConfig requestConfig = builder.setConnectTimeout(timeout).setSocketTimeout(timeout).build();
			method.setConfig(requestConfig);
		}
		method.setEntity(requestEntity);
		byte[] datas = new byte[0];
		try {
			HttpResponse response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			datas = EntityUtils.toByteArray(entity);
			EntityUtils.consume(entity);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			method.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}
		return datas;
	}

	/**
	 * POST请求
	 * 
	 * @param uri 请求的uri地址
	 * @param parameters 查询参数
	 * @param requestEntity POST请求报文体
	 * @return HttpResponse
	 */
	public static HttpResponse executePostAsStream(String uri, List<NameValuePair> parameters,
			HttpEntity requestEntity) {
		return executePostAsStream(uri, parameters, requestEntity, 0);
	}

	/**
	 * POST请求
	 * 
	 * @param uri 请求的uri地址
	 * @param parameters 查询参数
	 * @param requestEntityPOST请求报文体
	 * @param timeout 连接超时时间
	 * @return HttpResponse
	 */
	public static HttpResponse executePostAsStream(String uri, List<NameValuePair> parameters, HttpEntity requestEntity,
			int timeout) {
		String requestURL = uri;
		if (parameters != null && !parameters.isEmpty())
			requestURL = uri + "?" + URLEncodedUtils.format(parameters, getCharset(null));
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost method = new HttpPost(requestURL);
		if (timeout > 0) {
			Builder builder = RequestConfig.custom();
			RequestConfig requestConfig = builder.setConnectTimeout(timeout).setSocketTimeout(timeout).build();
			method.setConfig(requestConfig);
		}
		method.setEntity(requestEntity);
		try {
			return httpClient.execute(method);
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * POST请求
	 * 
	 * @param uri 请求的uri地址
	 * @param parameters 查询参数
	 * @param requestEntity POST请求需要传递报文数据
	 * @return String
	 */
	public static String executePostAsString(String uri, List<NameValuePair> parameters, HttpEntity requestEntity) {
		return executePostAsString(uri, parameters, requestEntity, getCharset(null), 0);
	}

	/**
	 * POST请求
	 * 
	 * @param uri 请求的uri地址
	 * @param parameters 查询参数
	 * @param requestEntity POST请求需要传递的报文数据
	 * @param charset 返回结果的字符编码
	 * @param timeout 请求超时时间
	 * @return String
	 */
	public static String executePostAsString(String uri, List<NameValuePair> parameters, HttpEntity requestEntity,
			String charset, int timeout) {
		byte[] datas = executePost(uri, parameters, requestEntity, timeout);
		return new String(datas, Charset.forName(getCharset(charset)));
	}

	/**
	 * POST请求
	 * 
	 * @param httpClient
	 * @param url 完整URL，包含查询参数，如
	 *            <code>https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET</code>
	 * @param entity POST请求报文要传递的报文数据
	 * @param reffer
	 * @param cookie
	 * @param timeout 超时时间
	 * @param charset
	 * @return
	 */
	public static String executePostAsString(CloseableHttpClient httpClient, String url, HttpEntity entity,
			String reffer, String cookie, int timeout, String charset) {
		CloseableHttpResponse httpResponse = null;
		if (httpClient == null) {
			httpClient = createCustomHttpClient(timeout, timeout);
		}
		HttpPost postMethod = new HttpPost(url);
		if (StringUtils.isNotBlank(cookie)) {
			postMethod.setHeader("Cookie", cookie);
		}
		if (StringUtils.isNotBlank(reffer)) {
			postMethod.setHeader("Reffer", reffer);
		}
		if (entity != null) {
			postMethod.setEntity(entity);
		}
		String result = "";
		try {
			httpResponse = httpClient.execute(postMethod);
			if (httpResponse == null || httpResponse.getEntity() == null) {
				return result;
			}
			HttpEntity resultEntity = httpResponse.getEntity();
			result = EntityUtils.toString(resultEntity, getCharset(charset));
			EntityUtils.consume(resultEntity);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			postMethod.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}
		return result;
	}

	/**
	 * 创建自定义的HttpClient
	 * 
	 * @param socketTimeout socket超时时间
	 * @param connectTimeout 连接超时时间
	 * @param secutiryProtocol 安全协议，如：<code>TLS, TLSv1.2, SSLv3</code>
	 * @return
	 */
	public static CloseableHttpClient createCustomHttpClient(int socketTimeout, int connectTimeout,
			String secutiryProtocol) {
		Builder builder = RequestConfig.custom();

		// 设置连接超时时间，单位毫秒
		builder.setConnectTimeout(connectTimeout);

		// 设置从connect manager获取的connection超时时间，毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的
		builder.setConnectionRequestTimeout(connectTimeout);

		// 请求获取数据的超时时间，单位毫秒
		builder.setSocketTimeout(socketTimeout);
		Collection<String> authSchemes = Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST);
		RequestConfig defaultRequestConfig = builder.setCookieSpec(CookieSpecs.STANDARD).setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(authSchemes)
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

		// 开启HTTPS支持
		SSLConnectionSocketFactory socketFactory = getSSLConnectionFactory(secutiryProtocol);

		// 创建可用的Scheme
		RegistryBuilder<ConnectionSocketFactory> regBuilder = RegistryBuilder.create();
		Registry<ConnectionSocketFactory> socketFactoryRegistry = regBuilder
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
		HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		return httpClientBuilder.setConnectionManager(connectionManager).setDefaultRequestConfig(defaultRequestConfig)
				.build();
	}

	/**
	 * 创建自定义的HttpClient
	 * 
	 * @param socketTimeout
	 *            socket超时时间
	 * @param connectTimeout
	 *            连接超时时间
	 * @return
	 */
	public static CloseableHttpClient createCustomHttpClient(int socketTimeout, int connectTimeout) {
		return createCustomHttpClient(socketTimeout, connectTimeout, "TLS");
	}

	private static SSLConnectionSocketFactory getSSLConnectionFactory(String protocol) {
		SSLConnectionSocketFactory socketFactory = null;
		TrustManager manager = new X509TrustManager() {

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// do not care
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// do not care
			}

		};
		try {
			SSLContext context = SSLContext.getInstance(protocol);
			context.init(null, new TrustManager[] { manager }, null);
			socketFactory = new SSLConnectionSocketFactory(context, (host, session) -> true);
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return socketFactory;
	}

	/**
	 * 文件上传，POST请求
	 * 
	 * @param httpClient {@link #createCustomHttpClient(int, int)}
	 * @param url 请求完整URL，包含查询参数，如
	 *            <code>https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET</code>
	 * @param localFilePath 待上传文件的本地路径
	 * @param partName 表单名称（key）
	 * @return
	 */
	public static String executeUploadFile(CloseableHttpClient httpClient, String url, String localFilePath,
			String partName) {
		return executeUploadFile(httpClient, url, localFilePath, partName, null);
	}

	/**
	 * 文件上传，POST请求
	 * 
	 * @param httpClient {@link #createCustomHttpClient(int, int)}
	 * @param url 请求完整URL，包含查询参数，如
	 *            <code>https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET</code>
	 * @param localFilePath 待上传文件的本地路径
	 * @param partName 表单名称（key）
	 * @param contentParts 上传文件时需要的附属表单信息
	 * @return
	 */
	public static String executeUploadFile(CloseableHttpClient httpClient, String url, String localFilePath,
			String partName, Map<String, ContentBody> contentParts) {
		CloseableHttpResponse httpResponse = null;
		if (httpClient == null) {
			httpClient = createCustomHttpClient(60000, 0);
		}

		File localFile = new File(localFilePath);

		// 以浏览器兼容模式运行，防止文件名乱码
		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addBinaryBody(partName, localFile, ContentType.MULTIPART_FORM_DATA, localFile.getName());
		if (contentParts != null && contentParts.size() > 0) {
			Iterator<String> it = contentParts.keySet().iterator();
			while (it.hasNext()) {
				String partid = it.next();
				entityBuilder.addPart(partid, contentParts.get(partid));
			}
		}
		HttpEntity requestEntity = entityBuilder.build();

		// uploadFile对应服务端类的同名属性<File类型>
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(requestEntity);
		String result = "";

		try {
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse == null || httpResponse.getEntity() == null) {
				return result;
			}
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity, getCharset(""));
			EntityUtils.consume(entity);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			httpPost.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}
		return result;
	}

	protected static String getCharset(String charset) {
		if (StringUtils.isBlank(charset)) {
			return "UTF-8";
		} else {
			return charset;
		}
	}

}
