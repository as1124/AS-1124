package com.primeton.mobile.thirdparty.wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.UnsupportedCharsetException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

public class HttpFileUpload {

//	private static SSLConnectionSocketFactory socketFactory = null;

	// https网站一般情况下使用了安全系数较低的SHA-1签名，因此首先我们在调用SSL之前需要重写验证方法，取消检测SSL。
	private static TrustManager manager = new X509TrustManager() {

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			
		}

	};

	/**
	 * @param retryTimes 重试次数
	 * @param socketTimeout 超时时间
	 * @return
	 */
	public static CloseableHttpClient createHttpClient(int retryTimes, int socketTimeout) {
		Builder builder = RequestConfig.custom();

		// 设置连接超时时间，单位毫秒
		builder.setConnectTimeout(5000);

		// 设置从connect manager获取的connection超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的
		builder.setConnectionRequestTimeout(1000);

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
		if (retryTimes > 0) {
			setRetryHandler(httpClientBuilder, retryTimes);
		}
		CloseableHttpClient httpClient = httpClientBuilder
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(defaultRequestConfig).build();
		return httpClient;
	}

	public static String executeGet(CloseableHttpClient httpClient, String url, String reffer, String cookie, 
			String charset, boolean closeHttpClient) throws IOException {
		CloseableHttpResponse httpResponse = null;
		if (httpClient == null) {
			httpClient = createHttpClient(0, 6000);
		}
		HttpGet getMethod = new HttpGet(url);
		if (cookie != null && cookie.equals("") == false) {
			getMethod.setHeader("Cookie", cookie);
		}
		if (reffer != null && reffer.equals("") == false) {
			getMethod.setHeader("Reffer", reffer);
		}
		charset = getCharset(charset);
		httpResponse = httpClient.execute(getMethod);
		return getResult(httpResponse, charset);
	}

	public static String executePost(CloseableHttpClient httpClient, String url, Object paramsObj, String reffer, 
			String cookie, String charset, boolean closeHttpClient) throws IOException {
		CloseableHttpResponse httpResponse = null;
		if (httpClient == null) {
			httpClient = createHttpClient(0, 6000);
		}
		HttpPost postMethod = new HttpPost(url);
		if (cookie != null && cookie.equals("") == false) {
			postMethod.setHeader("Cookie", cookie);
		}
		if (reffer != null && reffer.equals("") == false) {
			postMethod.setHeader("Reffer", reffer);
		}
		charset = getCharset(charset);

		// 设置参数
		HttpEntity httpEntity = getEntity(paramsObj, charset);
		if (httpEntity != null) {
			postMethod.setEntity(httpEntity);
		}
		httpResponse = httpClient.execute(postMethod);
		return getResult(httpResponse, charset);

	}

	public static String executeUploadFile(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath, 
			String charset, boolean closeHttpClient) throws ClientProtocolException, IOException {
		CloseableHttpResponse httpResponse = null;
		if (httpClient == null) {
			httpClient = createHttpClient(0, 6000);
		}

		// 把文件转换成流对象FileBody
		File localFile = new File(localFilePath);
		FileBody fileBody = new FileBody(localFile);

		// 以浏览器兼容模式运行，防止文件名乱码
		HttpEntity requestEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addPart("uploadFile", fileBody).setCharset(CharsetUtils.get("UTF-8")).build();

		// uploadFile对应服务端类的同名属性<File类型>
		HttpPost httpPost = new HttpPost(remoteFileUrl);
		httpPost.setEntity(requestEntity);
		httpResponse = httpClient.execute(httpPost);
		return getResult(httpResponse, charset);
	}

	public static boolean executeDownloadFile(CloseableHttpClient httpClient, String remoteFileUrl, String localFile, 
			String charset, boolean closeHttpClient) throws ClientProtocolException, IOException {
		CloseableHttpResponse response = null;
		InputStream in = null;
		FileOutputStream fout = null;
		try {
			HttpGet httpget = new HttpGet(remoteFileUrl);
			response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return false;
			}
			in = entity.getContent();
			File file = new File(localFile);
			fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp, 0, l);
			}
			fout.flush();
			EntityUtils.consume(entity);
			return true;
		} finally {
			if (fout != null) {
				fout.close();
			}
			if (response != null) {
				response.close();
			}
			if (closeHttpClient && httpClient != null) {
				httpClient.close();
			}
		}

	}

	private static HttpEntity getEntity(Object paramsObj, String charset)
			throws UnsupportedCharsetException, IOException {
		if (paramsObj == null) {
			return null;
		}
		if (Map.class.isInstance(paramsObj)) {
			Map<String, String> paramsMap = (Map<String, String>) paramsObj;
			List<NameValuePair> list = getNameValuePairs(paramsMap);
			UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(list);
			httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED
					.getMimeType());
			return httpEntity;
		} else if (String.class.isInstance(paramsObj)) {
			String paramStr = paramsObj.toString();
			StringEntity httpEntity = new StringEntity(paramStr, charset);
			if (paramStr.startsWith("{")) {
				httpEntity.setContentType(ContentType.APPLICATION_JSON
						.getMimeType());
			} else if (paramStr.startsWith("<")) {
				httpEntity.setContentType(ContentType.APPLICATION_XML
						.getMimeType());
			} else {
				httpEntity
						.setContentType(ContentType.APPLICATION_FORM_URLENCODED
								.getMimeType());
			}
			return httpEntity;
		} else {
			return null;
		}
	}

	private static String getResult(CloseableHttpResponse httpResponse,
			String charset) throws IOException {
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

	private static String getCharset(String charset) {
		return charset == null ? "UTF-8" : charset;
	}

	private static List<NameValuePair> getNameValuePairs(
			Map<String, String> paramsMap) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (paramsMap == null || paramsMap.isEmpty()) {
			return list;
		}
		for (Entry<String, String> entry : paramsMap.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return list;
	}

	private static SSLConnectionSocketFactory getSSLConnectionFactory() {
		SSLConnectionSocketFactory socketFactory = null;
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
	 * 为httpclient设置重试信息
	 * 
	 * @param httpClientBuilder
	 * @param retryTimes
	 */
	private static void setRetryHandler(HttpClientBuilder httpClientBuilder, final int retryTimes) {
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= retryTimes) {
					// Do not retry if over max retry count
					return false;
				}
				if (exception instanceof InterruptedIOException) {
					// Timeout
					return false;
				}
				if (exception instanceof UnknownHostException) {
					// Unknown host
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {
					// Connection refused
					return false;
				}
				if (exception instanceof SSLException) {
					// SSL handshake exception
					return false;
				}
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// 如果请求被认为是幂等的，那么就重试
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}
		};
		httpClientBuilder.setRetryHandler(myRetryHandler);
	}
}
