package com.primeton.mobile.thirdparty.wechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.thirdparty.access.AbstractAccessToken;
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.primeton.mobile.thirdparty.wechat.model.menu.WechatMenu;
import com.primeton.mobile.thirdparty.wechat.model.menu.WechatMenuConfiguration;
import com.primeton.mobile.thirdparty.wechat.model.menu.WechatMenuRuler;

/**
 * 微信公众号菜单操作
 * <ol>
 * <li>自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。
 * <li>一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。
 * <li>创建自定义菜单后，菜单的刷新策略是，在用户进入公众号会话页或公众号profile页时，如果发现上一次拉取
 * 菜单的请求在5分钟以前，就会拉取一下菜单，如果菜单有更新，就会刷新客户端的菜单。测试时可以尝试取消关注公众账号
 * 后再次关注，则可以看到创建后的效果。
 * </ol>
 * 
 * <p>
 * <strong>菜单类型</strong>
 * <li><code>click</code>：点击推事件
 * <li><code>view</code>：跳转URL
 * <li><code>scancode_push</code>：扫码推事件
 * <li><code>scancode_waitmsg</code>：扫码推事件且弹出“消息接收中”提示框
 * <li><code>pic_sysphoto</code>：弹出系统拍照发图
 * <li><code>pic_photo_or_album</code>：弹出拍照或者相册发图
 * <li><code>pic_weixin</code>：弹出微信相册发图器
 * <li><code>location_select</code>：弹出地理位置选择器
 * <li><code>media_id</code>：下发消息（除文本消息）
 * <li><code>view_limited</code>：跳转图文消息URL <br><br>
 * </p>
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class MenusOperations {

	/**
	 * 创建菜单
	 * @param token
	 * @param menus 
	 * @return
	 */
	public boolean addNormalMenu(AbstractAccessToken token, WechatMenu[] menus) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/create";
		List<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		JSONArray buttons = new JSONArray();
		buttons.addAll(Arrays.asList(menus));
		postData.put("button", buttons);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 查询普通类型微信菜单
	 * @param token
	 */
	public WechatMenu[] getNormalMenus(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/get";
		List<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	// 普通的自定义菜单
        	JSONObject normalMenus = JSONObject.parseObject(result).getJSONObject("menu");
        	JSONArray buttons = normalMenus.getJSONArray("button");
        	
        	WechatMenu[] commonMnus = parseMenus(buttons);
        	return commonMnus;
		} else if(IWechatConstants.RETURN_CODE_NO_MENUS.equals(returnCode)){
			return new WechatMenu[0];
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 查询个性化菜单（条件菜单）
	 * @param token
	 * @throws ThirdPartyRequestExceprion 
	 */
	public WechatMenu[] getConditionalMenus(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/get";
		List<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	WechatMenu[] condMenus = new WechatMenu[0];
        	JSONObject conditionalMenus = JSONObject.parseObject(result).getJSONObject("conditionalmenu");
        	if(conditionalMenus != null){
	        	JSONArray buttons = conditionalMenus.getJSONArray("button");
	        	condMenus = parseMenus(buttons);
        	}
        	return condMenus;
		} else if(IWechatConstants.RETURN_CODE_NO_MENUS.equals(returnCode)){
			return new WechatMenu[0];
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	private WechatMenu[] parseMenus(JSONArray buttons){
		WechatMenu[] menus = new WechatMenu[buttons.size()];
		for(int i=0; i<buttons.size(); i++){
			JSONObject child = buttons.getJSONObject(i);
			menus[i] = new WechatMenu(child.getString("name"), child.getString("key"), child.getString("type"), 
					child.getString("url"), child.getString("media_id"));
			JSONArray childMenus = child.getJSONArray("sub_button");
			for(int j=0; j<childMenus.size(); j++){
				child = childMenus.getJSONObject(j);
				WechatMenu childMenu = new WechatMenu(child.getString("name"), child.getString("key"), child.getString("type"), 
						child.getString("url"), child.getString("media_id"));
				menus[i].addSub_button(childMenu);
			}
		}
		return menus;
	}
	
	/**
	 * 删除菜单；普通菜单及个性化菜单都将被删除。
	 * @param token
	 * @return
	 */
	public boolean deleteAllMenus(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/delete";
		List<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else return false;
	}
	
	/**
	 * 添加个性化菜单
	 * <p>通过该接口，让公众号的不同用户群体看到不一样的自定义菜单。该接口开放给<strong><code>已认证</code></strong>订阅号和已认证服务号。
	 * 开发者可以通过以下条件来设置用户看到的菜单：
	 * <ol>
	 * <li>用户分组（开发者的业务需求可以借助用户分组来完成）
	 * <li>性别
	 * <li>手机操作系统
	 * <li>地区（用户在微信客户端设置的地区）
	 * <li>语言（用户在微信客户端设置的语言
	 * </ol>
	 * </p>
	 * @param token
	 * @param menus
	 * @param matcheRuler 菜单匹配规则
	 * @return
	 * @throws ThirdPartyRequestExceprion 
	 */
	public boolean addConditionalMenus(AbstractAccessToken token, WechatMenu[] menus, WechatMenuRuler matcheRuler) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/addconditional";
		List<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		JSONArray buttons = new JSONArray();
		buttons.addAll(Arrays.asList(menus));
		postData.put("button", buttons);
		postData.put("matchrule", matcheRuler);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 删除个性化菜单
	 * 
	 * @param token
	 * @param menuid 菜单id，可以通过菜单查询接口获取
	 * @return
	 */
	public boolean deleteConditionalMenus(AbstractAccessToken token, String menuid){
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/delconditional";
		List<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		postData.put("menuid", menuid);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 测试个性化菜单匹配结果
	 * @param token
	 * @param userid 测试用户的openid或者微信号
	 * @return
	 */
	public WechatMenu[] testMatchRule(AbstractAccessToken token, String userid){
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/trymatch";
		List<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		postData.put("user_id", userid);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	JSONArray buttons = JSONObject.parseObject(result).getJSONArray("button");
        	return parseMenus(buttons);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return new WechatMenu[0];
		}
	}
	
	/**
	 * 获取自定义菜单配置信息, 菜单删除后配置可能还存在.
	 * @param token
	 * @return
	 */
	public WechatMenuConfiguration getMenuConfiguration(AbstractAccessToken token){
		String uri = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info";
		List<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMenuConfiguration.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
}
