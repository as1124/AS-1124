package com.primeton.mobile.wechat;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;


public class Test {

	public static void test(){
//		HttpClient hc = new HttpClient();  
//        List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
//       nvps.add(new NameValuePair("uerName", ""));  
//       nvps.add(new NameValuePair("userCode", ""));  
//       
//       //请根据实际修改上送包xml数据,POST请求没有长度限制，get请求太长会报错,根据实际情况，
//       //一般的数据传送会要求进行签名、BASE64编码或者压缩等机制进行传输  
//       nvps.add(new NameValuePair("reqData", ""));    
////       UrlEncodedFormEntity   urlEncodedFormEntity = new UrlEncodedFormEntity(nvps,"GBK");  
////       String url = "https://"+yourConnectIp+":"+yourConnectPort;  
//       //加载证书  
//       java.security.KeyStore trustStore = java.security.KeyStore.getInstance(java.security.KeyStore.getDefaultType());  
//       //"123456"为制作证书时的密码  
//       trustStore.load(new FileInputStream(new File("你的证书位置")), "123456".toCharArray());  
//       org.apache.http.conn.ssl.SSLSocketFactory socketFactory = new org.apache.http.conn.ssl.SSLSocketFactory(trustStore);  
//       //不校验域名  
//       socketFactory.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
//       //这个8446是和被访问端约定的端口，一般为443  
//       org.apache.http.conn.scheme.Scheme sch = new org.apache.http.conn.scheme.Scheme("https", socketFactory, 8446);  
//       hc.getConnectionManager().getSchemeRegistry().register(sch);  
//       org.apache.http.client.methods.HttpPost hr = new org.apache.http.client.methods.HttpPost(url);  
//
//       hr.setEntity(urlEncodedFormEntity);  
//       hr.setHeader("Content-Type", "application/x-www-form-urlencoded");  
//       org.apache.http.HttpResponse hres = hc.execute(hr);  
//       org.apache.http.HttpEntity entity = hres.getEntity();  
//       re_code = hres.getStatusLine().statusCode;  
//       if (re_code == 200) {  
//       //your successCode here  
//       String repMsg = org.apache.http.util.EntityUtils.toString(entity,"GBK");  
//       }else{  
//       //your failCode here  
//       }  
         
    }
}
