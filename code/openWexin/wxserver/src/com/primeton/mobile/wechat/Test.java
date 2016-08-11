package com.primeton.mobile.wechat;

import java.util.Map;

import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.menu.WechatMenu;
import com.primeton.mobile.wechat.model.menu.WechatMenuConfiguration;


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
	
	
	public static void main(String[] args){
		try {
//			AccessToken token = AccessToken.getToken("wxfad56e1d17a29e04", "d048ee6acfb69cbd0fc3aaa591b1455b");
			WechatMenu parent1 = new WechatMenu("What's This", "parent1");
			WechatMenu parent2 = new WechatMenu("PWorld", "parent2");
			
			WechatMenu parent11 = new WechatMenu("点击菜单", "11", WechatMenu.TYPE_CLICK, "", "");
			parent1.addSub_button(parent11);
			WechatMenu parent12 = new WechatMenu("链接菜单", "12", WechatMenu.TYPE_VIEW, "http://www.primeton.com", "");
			parent1.addSub_button(parent12);
			WechatMenu parent13 = new WechatMenu("扫码", "13", WechatMenu.TYPE_SCANCODE_PUSH, "", "");
			parent1.addSub_button(parent13);
			WechatMenu parent14 = new WechatMenu("扫码等待", "14", WechatMenu.TYPE_SCANCODE_WAIT_MSG, "", "");
			parent1.addSub_button(parent14);
			WechatMenu parent15 = new WechatMenu("拍照", "15", WechatMenu.TYPE_SYSPHOTO, "", "");
			parent1.addSub_button(parent15);
			
			WechatMenu parent21 = new WechatMenu("选择相册", "21", WechatMenu.TYPE_PHOTO_OR_ALBUM, "", "");
			parent2.addSub_button(parent21);
			WechatMenu parent22 = new WechatMenu("微信相册", "22", WechatMenu.TYPE_PIC_WEIXIN, "", "");
			parent2.addSub_button(parent22);
			WechatMenu parent23 = new WechatMenu("地理位置", "23", WechatMenu.TYPE_LOCATION_SELECT, "", "");
			parent2.addSub_button(parent23);
			//WechatMenu parent24 = new WechatMenu("接收素材", "24", WechatMenu.TYPE_MEDIA_ID, "", "");
			//parent2.addSub_button(parent24);
			//WechatMenu parent25 = new WechatMenu("跳转图文", "15", WechatMenu.TYPE_VIEW_LIMITED, "", "");
			//parent2.addSub_button(parent25);
			
//			boolean flag = MenusOperations.createMenu(token, new WechatMenu[]{parent1, parent2});
//			boolean result = MenusOperations.deleteMenus(token);
			AccessToken token = new AccessToken();
			token.setAccess_token("3INEaJzStGZ-4hLL3F0Fh7RoXMhCSQ_eEGRifJam6JojPnspowQcC3rIu3EA_2cgIRZS8BT9qPzk-lU3o_-mv-N02itXzau_gw1sNLRjs9i3lolO5ist4DaFtgeUKh_uFDGjAGAAYB");
			WechatMenuConfiguration config = MenusOperations.getMenuConfiguration(token);
			
			System.out.println("aaaa");
		} catch (WechatExceprion e) {
			e.printStackTrace();
		}
		
	}
}
