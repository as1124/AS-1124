package com.as1124.api.wechat.model.menu;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.as1124.api.wechat.model.IWechatModel;

/**
 * 当前公众号自定义菜单的配置信息.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class WechatMenuConfiguration implements IWechatModel {

	/**
	 * 菜单是否开启, <code>0</code>代表未开启, 
	 * <code>1</code>代表开启
	 */
	private int is_menu_open = 0;

	/**
	 * 菜单信息
	 */
	private String selfmenu_info = "";

	public WechatMenuConfiguration() {
		// default constructor
	}

	/**
	 * 获取当前菜单状态
	 * @see #is_menu_open
	 * @return
	 */
	public int getIs_menu_open() {
		return is_menu_open;
	}

	/**
	 * 设置菜单状态
	 * @param isMenuOpen
	 */
	public void setIs_menu_open(int isMenuOpen) {
		this.is_menu_open = isMenuOpen;
	}

	/**
	 * 获取菜单信息的json串形式
	 * @return
	 */
	public String getSelfmenu_info() {
		return this.selfmenu_info;
	}

	/**
	 * 将菜单信息的JSON串解析成java对象返回
	 * @return List<WechatMenu>
	 */
	public List<WechatMenu> parseInfo2Menu() {
		String butoonStr = JSONObject.parseObject(this.selfmenu_info).getString("button");
		JSONArray array = JSONObject.parseArray(butoonStr);
		JSONArray newArray = new JSONArray();
		for (int i = 0; i < array.size(); i++) {
			JSONObject json = array.getJSONObject(i);
			JSONArray subButtons = json.getJSONObject("sub_button").getJSONArray("list");
			json.remove("sub_button");
			json.put("sub_button", subButtons);
			newArray.add(json);
		}
		return JSONObject.parseArray(newArray.toJSONString(), WechatMenu.class);
	}

	/**
	 * @see #selfmenu_info
	 * @param selfMenuInfo
	 */
	public void setSelfmenu_info(String selfMenuInfo) {
		this.selfmenu_info = selfMenuInfo;
	}

	/**
	 * 将java模型反序列化成JSON串形式的，以此来描述菜单信息
	 * @param menus
	 */
	public void parseMenu2Json(List<WechatMenu> menus) {
		JSONArray buttons = new JSONArray();
		buttons.addAll(menus);
		this.selfmenu_info = JSONArray.toJSONString(buttons);
	}

}
