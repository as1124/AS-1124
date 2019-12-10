package com.as1124.api.wechat.model.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.as1124.api.wechat.model.IWechatModel;

/**
 * 微信菜单模型.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class WechatMenu implements IWechatModel {

	/**
	 * 公众号菜单类型枚举
	 *
	 * @author huangjw (mailto:as1124huang@gmail.com)
	 */
	public enum MenuType {
		/**
		 * 点击型菜单，需填写菜单key
		 * 
		 * @see WechatMenu#setKey(String)
		 */
		TYPE_CLICK("click"),

		/**
		 * 跳转URL型菜单, 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL
		 * 
		 * @see WechatMenu#setUrl(String)
		 */
		TYPE_VIEW("view"),

		/**
		 * 扫码推类型菜单, 用户点击按钮后，微信客户端将调起扫一扫工具，
		 * 完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。
		 * 
		 * @see WechatMenu#setKey(String)
		 */
		TYPE_SCANCODE_PUSH("scancode_push"),

		/**
		 * 扫码推事件且弹出“消息接受中”提示框
		 * 
		 * @see WechatMenu#setKey(String)
		 */
		TYPE_SCANCODE_WAITMSG("scancode_waitmsg"),

		/**
		 * 弹出系统拍照发图
		 * 
		 * @see WechatMenu#setKey(String)
		 */
		TYPE_PIC_SYSPHOTO("pic_sysphoto"),

		/**
		 * 弹出拍照或者相册发图
		 * 
		 * @see WechatMenu#setKey(String)
		 */
		TYPE_PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),

		/**
		 * 弹出微信相册发图器
		 * 
		 * @see WechatMenu#setKey(String)
		 */
		TYPE_PIC_WEIXIN("pic_weixin"),

		/**
		 * 地理位置选择菜单，完成选择操作后，将选择的地理位置发送给开发者的服务器
		 * 
		 * @see WechatMenu#setKey(String)
		 */
		TYPE_LOCATION_SELECT("location_select"),

		/**
		 * 下拉消息（除文本消息），用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，
		 * 可以是图片、音频、视频、图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
		 * 
		 * @see WechatMenu#setMedia_id(String)
		 */
		TYPE_MEDIA_ID("media_id"),

		/**
		 * 跳转图文消息URL用户点击view_limited类型按钮后
		 * 
		 * @see WechatMenu#setMedia_id(String)
		 */
		TYPE_VIEW_LIMITED("view_limited"),

		/**
		 * 打开小程序菜单，需要填写小程序的appid，打开的页面pagePath
		 * 
		 * @see WechatMenu#setAppid(String)
		 * @see WechatMenu#setPagepath(String)
		 */
		TYPE_MINI_PROGRAM("miniprogram");

		private String value;

		private MenuType(String typeName) {
			this.value = typeName;
		}

		/**
		 * @return Returns the value.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value The value to set.
		 */
		protected void setValue(String value) {
			this.value = value;
		}

	}

	/**
	 * 菜单类型
	 */
	private String type = MenuType.TYPE_CLICK.getValue();

	/**
	 * 菜单名称
	 */
	private String name = "";

	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节. click等点击类型必须
	 */
	private String key = "";

	/**
	 * view类型必须。网页链接，用户点击菜单可打开链接，不超过256字节
	 */
	private String url = "";

	/**
	 * media_id类型和view_limited类型必须，
	 * 调用新增永久素材接口返回的合法media_id
	 */
	private String media_id = "";

	/**
	 * 二级菜单列表
	 */
	private List<WechatMenu> sub_button = new ArrayList<>(5);

	/**
	 * 小程序的APPID<BR/>
	 * Comment for <code>appid</code>
	 */
	private String appid = "";

	/**
	 * 小程序的页面相对路径<BR/>
	 * Comment for <code>pagepath</code>
	 */
	private String pagepath = "";

	public WechatMenu() {
	}

	/**
	 * @param name
	 * @param key
	 * @param type
	 * @param url
	 * @param mediaId
	 */
	public WechatMenu(String name, String key, MenuType type, String url, String mediaId) {
		this.name = name;
		this.key = key;
		this.type = type.getValue();
		if (StringUtils.isNotBlank(url))
			this.url = url;
		if (StringUtils.isNotBlank(mediaId))
			this.media_id = mediaId;
	}

	/**
	 * 当前菜单类型
	 * @see #type
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置菜单类型
	 * @see #type
	 * @param type
	 */
	public void setType(String type) {
		if (StringUtils.isNotBlank(type)) {
			this.type = type;
		}
	}

	/**
	 * @see #name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see #name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see #key
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @see #key
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @see #url
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @see #url
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取二级菜单
	 * @see #sub_button
	 * @return
	 */
	public List<WechatMenu> getSub_button() {
		return this.sub_button;
	}

	/**
	 * 设置二级菜单
	 * @see #sub_button
	 * @param subButtons
	 */
	public void setSub_button(List<WechatMenu> subButtons) {
		this.sub_button = subButtons;
	}

	/**
	 * 添加一个二级菜单
	 * @see #sub_button
	 * @param subButton
	 */
	public void addSubButton(WechatMenu subButton) {
		if (this.sub_button == null)
			this.sub_button = new ArrayList<>();
		this.sub_button.add(subButton);
	}

	/**
	 * 获取永久素材的media_id
	 * @return
	 */
	public String getMedia_id() {
		return media_id;
	}

	/**
	 * media_id类型和view_limited类型必须 
	 * 
	 * @param mediaID
	 * @see #TYPE_MEDIA_ID
	 * @see #TYPE_VIEW_LIMITED
	 */
	public void setMedia_id(String mediaID) {
		this.media_id = mediaID;
	}

	/**
	 * @return Returns the appid.
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * @param appid The appid to set.
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @return Returns the pagepath.
	 */
	public String getPagepath() {
		return pagepath;
	}

	/**
	 * @param pagepath The pagepath to set.
	 */
	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}

}
