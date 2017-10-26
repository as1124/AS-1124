package com.mobile.thirdparty.wechat.model.menu;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.wechat.IWechatModel;

/**
 * 当前公众号自定义菜单的配置信息.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatMenuConfiguration implements IWechatModel{

	/**
	 * 菜单是否开启, <code>0</code>代表未开启, 
	 * <code>1</code>代表开启
	 */
	int is_menu_open;
	
	/**
	 * 菜单信息
	 */
	String selfmenu_info;
	
	public WechatMenuConfiguration() {
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
	 * @param is_menu_open
	 */
	public void setIs_menu_open(int is_menu_open) {
		this.is_menu_open = is_menu_open;
	}

	/**
	 * 获取菜单信息的json串形式
	 * @return
	 */
	public String getSelfmenu_info() {
		return this.selfmenu_info;
	}

	/**
	 * 将菜单信息的json串解析成java对象返回
	 * @return List<WechatMenu>
	 */
	public List<WechatMenu> parseInfo2Menu(){
		String butoonStr = JSONObject.parseObject(this.selfmenu_info).getString("button");
		JSONArray array = JSONObject.parseArray(butoonStr);
		JSONArray newArray = new JSONArray();
		for(int i=0; i<array.size(); i++){
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
	 * @param selfmenu_info
	 */
	public void setSelfmenu_info(String selfmenu_info) {
		this.selfmenu_info = selfmenu_info;
	}

	/**
	 * 将java模型反序列化成json串形式的，以此来描述菜单信息
	 * @param menus
	 */
	public void parseMenu2Json(List<WechatMenu> menus){
		JSONArray buttons = new JSONArray();
		buttons.addAll(menus);
		this.selfmenu_info = JSONArray.toJSONString(buttons);
	}
	
}
