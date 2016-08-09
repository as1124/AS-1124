package com.primeton.mobile.wechat.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.primeton.mobile.wechat.pay.WechatPay;


/**
 * 红包处理servlet
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@WebServlet("/MoneySurprise")
public class MoneySurprise extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoneySurprise() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}

	public static void main(String[] args){
		String uri = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
		ArrayList<String> nodes = new ArrayList<String>();
		String mchID = "1312377501";
		String paySecret = "primeton2016yidongceshiHAHAHAHAH";
		String openid = "ooyKpxIA8sqiWbSE89yJJl34uSsE";
		String nonceStr = RandomStringUtils
				.random(20,
						"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		nodes.add("nonce_str="+nonceStr);
		nodes.add("mch_billno="+MoneySurprise.getBillNo(mchID));
		nodes.add("mch_id="+mchID);
		nodes.add("wxappid=wxfad56e1d17a29e04");
		nodes.add("send_name=Primeton Software");
		nodes.add("re_openid="+openid);
		
		// 付款金额, 单位分
		nodes.add("total_amount="+100);
		nodes.add("total_num="+1);
		nodes.add("wishing=发钱了,HAHA");
		nodes.add("client_ip=192.168.6.205");
		nodes.add("act_name=普元PWorld大会活动");
		nodes.add("remark=备注：只有一毛钱");
		
		String[] arrays = nodes.toArray(new String[nodes.size()]);
		String sign = WechatPay.generateSign(arrays, paySecret);
		String postContent = WechatPay.getPostContent(arrays, sign);
		System.out.println(postContent);
		
		//使用带证书的HTTPClient
        try {
        	KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File("D:/cert/apiclient_cert.p12"));//加载本地的证书进行https加密传输
            keyStore.load(instream, mchID.toCharArray());//设置证书密码
            instream.close();
            
            // Trust own CA and all self-signed certs
            // HUANG 查看微信的协议
            SSLContext sslcontext = buildSSLContext(keyStore, mchID.toCharArray(), "");
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            HttpPost httppost = new HttpPost(uri);
            HttpEntity postentity = new StringEntity(postContent, ContentType.create("text/xml", "UTF-8"));
    		httppost.setEntity(postentity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        System.out.println(text);
                    }
                   
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static String getBillNo(String mchID){
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		String timeStart = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String tenNum = "1000010001";
		return mchID + timeStart +tenNum;
	}

	
	/**
	 * 
	 * 加载证书创建SSL安全会话
	 * 
	 * @param keystore
	 * @param keyPassword
	 * @param protocol
	 * @return
	 */
	public static SSLContext buildSSLContext(KeyStore keystore, char[] keyPassword, String protocol) {
		KeyManagerFactory kmfactory;
		try {
			kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmfactory.init(keystore, keyPassword);
			KeyManager[] kms = kmfactory.getKeyManagers();
			
			if(StringUtils.isBlank(protocol)){
				protocol = "TLS";
			}
			SSLContext sslcontext = SSLContext.getInstance(protocol);
			//增加参数设置
			sslcontext.init(kms, null, null);
			return sslcontext;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		return null;
	}
	
}
