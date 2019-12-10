package com.as1124.api.wechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.as1124.api.access.AbstractAccessToken;
import com.as1124.api.access.HttpExecuter;
import com.as1124.api.wechat.model.menu.WechatConditionMenu;
import com.as1124.api.wechat.model.menu.WechatMenu;
import com.as1124.api.wechat.model.menu.WechatMenuConfiguration;
import com.as1124.api.wechat.model.menu.WechatMenuRuler;
import com.as1124.common.util.LoggerUtil;

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
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 */
public class MenuOperations {

	static Logger logger = LoggerUtil.getLogger(MenuOperations.class);

	private MenuOperations() {
	}

	/**
	 * 创建普通类型微信菜单，原有的菜单将会被删除
	 * 
	 * @param token
	 * @param menus
	 * @return
	 */
	public static boolean addNormalMenus(AbstractAccessToken token, WechatMenu[] menus) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/create";
		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));

		JSONObject postData = new JSONObject();
		JSONArray buttons = new JSONArray();
		buttons.addAll(Arrays.asList(menus));
		postData.put("button", buttons);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 查询普通类型微信菜单
	 * 
	 * @param token
	 * @return
	 */
	public static WechatMenu[] getNormalMenus(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/get";
		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject resultJSON = JSONObject.parseObject(result);
		int returnCode = resultJSON.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			// 普通的自定义菜单
			JSONArray buttons = resultJSON.getJSONObject("menu").getJSONArray("button");
			return parseMenus(buttons);
		} else if (46003 == returnCode) {
			// 状态码标识没有菜单
			return new WechatMenu[0];
		} else {
			logger.log(Level.SEVERE, result);
			return new WechatMenu[0];
		}
	}

	/**
	 * 添加个性化菜单，原有的菜单将会被删除
	 * 
	 * <p>通过该接口，让公众号的不同用户群体看到不一样的自定义菜单。
	 * 该接口开放给<strong><code>已认证</code></strong>订阅号和已认证服务号。
	 * 开发者可以通过以下条件来设置用户看到的菜单：
	 * <ol>
	 * <li>用户标签分组（开发者的业务需求可以借助用户标签分组来完成）
	 * <li>性别
	 * <li>手机操作系统
	 * <li>地区（用户在微信客户端设置的地区）
	 * <li>语言（用户在微信客户端设置的语言
	 * </ol>
	 * </p>
	 * 
	 * @param token
	 * @param conditionMenus
	 * @return 个性化菜单对应的id
	 */
	public static String addConditionalMenus(AbstractAccessToken token, WechatConditionMenu conditionMenus) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/addconditional";
		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		JSONArray buttons = new JSONArray();
		buttons.addAll(Arrays.asList(conditionMenus.getButton()));
		postData.put("button", buttons);
		postData.put("matchrule", conditionMenus.getMatchrule());
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject resultJSON = JSONObject.parseObject(result);
		if (resultJSON.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return resultJSON.getString("menuid");
		} else {
			logger.log(Level.SEVERE, result);
			return "";
		}
	}

	/**
	 * 查询个性化菜单（条件菜单）
	 * 
	 * @param token
	 * @return
	 */
	public static WechatConditionMenu[] getConditionalMenus(AbstractAccessToken token) {
		WechatConditionMenu[] condMenus = null;
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/get";
		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject resultJSON = JSONObject.parseObject(result);
		int returnCode = resultJSON.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			JSONArray conditionalMenus = resultJSON.getJSONArray("conditionalmenu");
			condMenus = new WechatConditionMenu[conditionalMenus.size()];
			for (int i = 0; i < conditionalMenus.size(); i++) {
				JSONObject child = conditionalMenus.getJSONObject(i);
				JSONArray buttons = child.getJSONArray("button");
				condMenus[i] = new WechatConditionMenu();
				condMenus[i].setButton(parseMenus(buttons));
				condMenus[i].setMatchrule(child.getObject("matchrule", WechatMenuRuler.class));
				condMenus[i].setMenuid(child.getString("menuid"));
			}
			return condMenus;
		} else if (46003 == returnCode) {
			return new WechatConditionMenu[0];
		} else {
			logger.log(Level.SEVERE, result);
			return new WechatConditionMenu[0];
		}
	}

	private static WechatMenu[] parseMenus(JSONArray buttons) {
		WechatMenu[] menus = new WechatMenu[buttons.size()];
		for (int i = 0; i < buttons.size(); i++) {
			JSONObject child = buttons.getJSONObject(i);
			menus[i] = new WechatMenu();
			menus[i].setName(child.getString("name"));
			menus[i].setKey(child.getString("key"));
			menus[i].setType(child.getString("type"));
			menus[i].setUrl(child.getString("url"));
			menus[i].setMedia_id(child.getString("media_id"));
			menus[i].setAppid(child.getString("appid"));
			menus[i].setPagepath(child.getString("pagepath"));
			JSONArray childMenus = child.getJSONArray("sub_button");
			for (int j = 0; j < childMenus.size(); j++) {
				child = childMenus.getJSONObject(j);
				WechatMenu childMenu = new WechatMenu();
				childMenu.setName(child.getString("name"));
				childMenu.setKey(child.getString("key"));
				childMenu.setType(child.getString("type"));
				childMenu.setUrl(child.getString("url"));
				childMenu.setMedia_id(child.getString("media_id"));
				childMenu.setAppid(child.getString("appid"));
				childMenu.setPagepath(child.getString("pagepath"));
				menus[i].addSubButton(childMenu);
			}
		}
		return menus;
	}

	/**
	 * 删除菜单；普通菜单及个性化菜单都将被删除。
	 * 
	 * @param token
	 * @return
	 */
	public static boolean deleteAllMenus(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/delete";
		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 删除个性化菜单
	 * 
	 * @param token
	 * @param menuid
	 * @return
	 */
	public static boolean deleteConditionalMenus(AbstractAccessToken token, String menuid) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/delconditional";
		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("menuid", menuid);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 测试个性化菜单匹配结果
	 * 
	 * @param token
	 * @param userid 测试用户的openid或者微信号
	 * @return
	 */
	public static WechatMenu[] testMatchRule(AbstractAccessToken token, String userid) {
		String uri = "https://api.weixin.qq.com/cgi-bin/menu/trymatch";
		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("user_id", userid);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			JSONArray buttons = JSONObject.parseObject(result).getJSONObject("menu").getJSONArray("button");
			return parseMenus(buttons);
		} else {
			logger.log(Level.SEVERE, result);
			return new WechatMenu[0];
		}
	}

	/**
	 * 获取自定义菜单配置信息, 菜单删除后配置可能还存在.
	 * 
	 * @param token
	 * @return
	 */
	public static WechatMenuConfiguration getMenuConfiguration(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info";
		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject resultJSON = JSONObject.parseObject(result);
		int returnCode = resultJSON.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			WechatMenuConfiguration menuConfig = new WechatMenuConfiguration();
			JSONObject data = JSONObject.parseObject(result);
			menuConfig.setIs_menu_open(data.getIntValue("is_menu_open"));
			menuConfig.setSelfmenu_info(data.getString("selfmenu_info"));
			return menuConfig;
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

}
