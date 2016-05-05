package com.primeton.mobile.wechat;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eos.system.annotation.Bizlet;
import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.menu.WechatMenu;
import com.primeton.mobile.wechat.model.menu.WechatMenuConfiguration;

/**
 * WeChat菜单操作
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class MenusOperations {

	/**
	 * 创建菜单
	 * @param accessToken
	 * @param menuData 
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("createMenu")
	public static boolean createMenu(String accessToken, WechatMenu[] menus) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/create";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject postData = new JSONObject();
		JSONArray buttons = new JSONArray();
		buttons.addAll(Arrays.asList(menus));
		postData.put("button", buttons);
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString(), IWechatConstants.CONTENT_TYPE, 
				IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new WechatExceprion("[MenuOperations#createMenu]"+result);
	}
	
	/**
	 * 查询自定义菜单
	 * @param accessToken
	 * @return
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	@Bizlet("getMenus")
	public static WechatMenu[] getMenus(String accessToken) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/get";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	String buttonStr = JSONObject.parseObject(result).getJSONObject("menu").getString("button");
			return JSONObject.parseArray(buttonStr, WechatMenu.class).toArray(new WechatMenu[]{});
		}else if(IWechatConstants.RETURN_CODE_NO_MENUS.equals(returnCode)){
			return null;
		}
		else throw new WechatExceprion("[MenuOperations#getMenus]"+result);
	}
	
	/**
	 * 删除菜单
	 * @param accessToken
	 * @return
	 * @throws IOException
	 */
	@Bizlet("deleteMenus")
	public static boolean deleteMenus(String accessToken) throws IOException{
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/delete";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else return false;
	}
	
	/**
	 * 获取自定义菜单配置, 菜单删除后配置可能还存在.
	 * @param accessToken
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws HttpException 
	 * @throws WechatExceprion 
	 */
	@Bizlet("getMenuConfiguration")
	public static WechatMenuConfiguration getMenuConfiguration(String accessToken) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMenuConfiguration.class);
		}
		else throw new WechatExceprion("[MenuOperations#getMenuConfiguration]"+result);
	}
	
}
