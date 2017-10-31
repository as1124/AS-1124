package com.mobile.thirdparty.wechat.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.wechat.HotlineService;

public class TEST {

	public static void main(String[] args) {
		List<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>();
		//		//移动平台服务号
		//		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, "wxfad56e1d17a29e04"));
		//		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, "d048ee6acfb69cbd0fc3aaa591b1455b"));
		//
		//		//EAII
		//		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, "wx0b96b87f1deb8ffb"));
		//		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, "d6ef682a84531ed8e3ef0adc44390a40"));

		//		//MISSU的公众号
		//		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, "wx533f20f2961abf67"));
		//		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, "48e4a0ea6e734a5a58f1b7fff6a93fbc"));
		//		
		//		//公众号测试号
		//		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_ID, "wxc70c5d9aab4f6a2b"));
		//		param.add(new BasicNameValuePair(IWechatConstants.KEY_APP_SECRET, "f3ca23ccf76c301f2158862db65cfdad"));
		//
		//		try {
		//			WechatAccessToken token = AccessTokenFactory.getToken("huangjw", param, WechatAccessToken.class);
		//			WechatAccessToken token = new WechatAccessToken();
		//			token.setAccess_token("MveByun9Mb_IPXZyPM5zE_unkJ-l9QVn4s8Rz0Ln2xMypTvvnDuhpTLfjxP8QcRWbIpVnlg6kNSdxBF7GXfbSRc2TthcZjrXgbr8ycpMEfiI4FgutgCyK_UbooBbD3zSJTXaACAQAE");
		//			String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="
		//					+token.getAccess_token()+"&type=image";
		//			String result = HttpFileUpload.executeUploadFile(null, url, "D:/icon_app/kefu.jpg", "UTF-8", true);
		//			System.out.println(result);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//

		FileInputStream in = null;
		try {
			in = new FileInputStream(new File("D:/wenjuan.txt"));
			byte[] buffs = new byte[in.available()];
			in.read(buffs);
			in.close();
			String result = URLDecoder.decode(new String(buffs), "utf-8");
			System.out.println(result);

			String requestURL = "https://wj.qq.com/sur/collect_answer";
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			CloseableHttpClient httpClient = HttpExecuter.createCustomHttpClient(60 * 1000, 60 * 1000);
			requestURL = requestURL + "?" + URLEncodedUtils.format(parameters, "UTF-8");
			HttpPost method = new HttpPost(requestURL);
			method.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			method.setHeader("Accept-Encoding", "gzip, deflate");
			method.setHeader("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.8, en-GB; q=0.5, en; q=0.3");
			method.setHeader("Cache-Control", "no-cache");
//			method.setHeader(HTTP.CONTENT_LEN, "6184");
//			method.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
//			method.setHeader("Cookie", "pgv_pvid=1461483326; pgv_pvi=795770880; pgv_si=s6366810112");
			method.setHeader("Referer", "https://wj.qq.com/s/1624532/6505");
			method.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)");
			method.setHeader("X-Requested-With", "XMLHttpRequest");
//			HttpEntity requestEntity = new StringEntity(new String(buffs), ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
			HttpEntity requestEntity = MultipartEntityBuilder.create()
					.addTextBody("answer_survey", new String(buffs), ContentType.create("application/json", "UTF-8"))
					.addTextBody("survey_id", "1624532").build();
			method.setEntity(requestEntity);
			HttpResponse response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			byte[] datas = EntityUtils.toByteArray(entity);
			EntityUtils.consume(entity);
			method.releaseConnection();
			httpClient.close();
			System.out.println(new String(datas, "utf-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void addKefu(WechatAccessToken token) throws IOException {
		HotlineAccount account = new HotlineAccount();
		account.setKf_account("kf2001@gh_c27d5e63d22d");
		account.setNickname("元宝");
		account.setPassword("000000");
		HotlineService service = new HotlineService();
		//		service.addHotlineAccount(token, account);
		//		service.getAllServiceAccount(token.getAccess_token());
		service.setAccountImage(token, account.getKf_account(), "d:/icon.png");
		//		service.getOnlineServiceAccounts(token);

	}

	public void testMedia() {

	}
}
