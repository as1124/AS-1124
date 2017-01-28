package com.primeton.mobile.thirdparty.wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
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
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;


public class HttpFileUpload {

	public static CloseableHttpClient createHttpClient(){
		return createHttpClient(0, 5000);
	}
	
	public static CloseableHttpClient createHttpClient(int socketTimeout){
		return createHttpClient(0, 5000);
	}
	
	public static CloseableHttpClient createHttpClient(int retryTimes, int socketTimeout){
		Builder builder = RequestConfig.custom();
		
		//设置连接超时时间，单位毫秒
		builder.setConnectTimeout(5000);
		
		//设置从connect manager获取的connection超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的
		builder.setConnectionRequestTimeout(1000);
		
		//请求获取数据的超时时间，单位毫秒
		builder.setSocketTimeout(socketTimeout);
		RequestConfig defaultRequestConfig = builder.setCookieSpec(CookieSpecs.STANDARD)
				.setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
		
		//开启HTTPS支持
		enableSSL();
		
		//创建可用的Scheme
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", socketFactory).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		if(retryTimes > 0){
			setRetryHandler(httpClientBuilder, retryTimes);
		}
		CloseableHttpClient httpClient = httpClientBuilder.setConnectionManager(connectionManager).setDefaultRequestConfig(defaultRequestConfig);
		return httpClient;
	}
	
	public static String executeGet(CloseableHttpClient httpClient, String url, String reffer, String cookie, 
			String charset, boolean closeHttpClient) throws IOException {
		CloseableHttpResponse httpResponse = null;
		if(httpClient == null){
			httpClient = createHttpClient();
		}
		HttpGet getMethod = new HttpGet(url);
		if(cookie!=null && cookie.equals("")==false){
			getMethod.setHeader("Cookie", cookie);
		}
		if(reffer!=null && reffer.equals("")==false){
			getMethod.setHeader("Reffer", reffer);
		}
		charset = getCharset(charset);
		httpResponse = httpClient.execute(getMethod);
		return getResult(httpResponse, charset);
	}
	
	public static String executePost(CloseableHttpClient httpClient, String url, Object paramsObj, String reffer, String cookie, 
			String charset, boolean closeHttpClient) throws IOException {
		CloseableHttpResponse httpResponse = null;
		if(httpClient == null){
			httpClient = createHttpClient();
		}
		HttpPost postMethod = new HttpPost(url);
		if(cookie!=null && cookie.equals("")==false){
			postMethod.setHeader("Cookie", cookie);
		}
		if(reffer!=null && reffer.equals("")==false){
			postMethod.setHeader("Reffer", reffer);
		}
		charset = getCharset(charset);
		
		//设置参数
		HttpEntity httpEntity = getEntity(paramsObj, charset);
		if(httpEntity != null){
			postMethod.setEntity(httpEntity);
		}
		httpResponse = httpClient.execute(postMethod);
		return getResult(httpResponse, charset);
		
	}
	
	public static String executeUploadFile(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath,
			String charset, boolean closeHttpClient) throws ClientProtocolException, IOException {
		CloseableHttpResponse httpResponse = null;
		if(httpClient == null){
			httpClient = createHttpClient();
		}
		
		//把文件转换成流对象FileBody
		File localFile = new File(localFilePath);
		FileBody fileBody = new FileBody(localFile);
		
		//以浏览器兼容模式运行，防止文件名乱码
		HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addPart("uploadFile", fileBody).setCharset(CharsetUtils.get("UTF-8")).build();
		
		//uploadFile对应服务端类的同名属性<File类型>
		HttpPost httpPost = new HttpPost(remoteFileUrl);
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
			if(entity == null){
				return false;
			}
			in = entity.getContent();
			File file = new File(localFile);
			fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while((l=in.read(tmp))!=-1){
				fout.write(tmp, 0, l);
			}
			fout.flush();
			EntityUtils.consume(entity);
			return true;
		} finally {
			if(fout!=null){
				fout.close();
			}
			if(response!=null){
				response.close();
			}
			if(closeHttpClient && httpClient!=null){
				httpClient.close();
			}
		}
		
	}
	
	private static HttpEntity getEntity(Object paramsObj, String charset) throws UnsupportedCharsetException {
		if(paramsObj == null){
			return null;
		}
		if(Map.class.isInstance(paramsObj)){
			Map<String, String> paramsMap = (Map<String, String>) paramsObj;
			List<NameValuePair> list = getNameValuePairs(paramsMap);
			UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(list);
			httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
			return httpEntity;
		} else if(String.class.isInstance(paramsObj)){
			String paramStr = paramsObj.toString();
			StringEntity httpEntity = new StringEntity(paramStr, charset);
			if(paramStr.startsWith("{")){
				httpEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
			} else if(paramStr.startsWith("<")){
				httpEntity.setContentType(ContentType.APPLICATION_XML.getMimeType());
			} else {
				httpEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
			}
			return httpEntity;
		} else {
			return null;
		}
	}
	
	private static String getResult(CloseableHttpResponse httpResponse, String charset) throws IOException {
		String result = null;
		if(httpResponse == null){
			return result;
		}
		HttpEntity entity = httpResponse.getEntity();
		if(entity == null){
			return result;
		}
		result = EntityUtils.toString(entity, charset);
		EntityUtils.consume(entity);
		return result;
		
	}
	
	private static String getCharset(String charset){
		return charset==null?"UTF-8":charset;
	}
	
	private static List<NameValuePair> getNameValuePairs(Map<String, String> paramsMap){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if(paramsMap==null || paramsMap.isEmpty()){
			return list;
		}
		for(Entry<String, String> entry:paramsMap.entrySet()){
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return list;
	}
	
	private static void enableSSL(){
		try {
			SSLCON
			
		} catch (Exception e) {
			// HUANG: handle exception
		}
	}
}
