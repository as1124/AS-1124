package com.primeton.mobile.wechat.model.menu;

import java.util.ArrayList;
import java.util.List;

import com.primeton.mobile.wechat.model.IWechatModel;

/**
 * 微信菜单模型.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatMenu implements IWechatModel{

	/**
	 * <strong>点击推事件</strong><br>
	 * 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event	的结构给开发者（参考消息接口指南），
	 * 并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互。
	 * @see #setType(String)
	 */
	public static final String TYPE_CLICK = "click";
	
	/**
	 * <strong>跳转URL</strong><br>
	 * 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，
	 * 可与网页授权获取用户基本信息接口结合，获得用户基本信息。
	 * @see #setType(String)
	 */
	public static final String TYPE_VIEW = "view";
	
	/**
	 * <strong>扫码推事件</strong><br>
	 * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），
	 * 且会将扫码的结果传给开发者，开发者可以下发消息。
	 * @see #setType(String)
	 */
	public static final String TYPE_SCANCODE_PUSH = "scancode_push";
	
	/**
	 * <strong>扫码推事件且弹出“消息接受中”提示框</strong><br>
	 * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，
	 * 同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
	 * @see #setType(String)
	 */
	public static final String TYPE_SCANCODE_WAIT_MSG = "scancode_waitmsg";
	
	/**
	 * <strong>弹出系统拍照发图</strong><br>
	 * 用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，
	 * 同时收起系统相机，随后可能会收到开发者下发的消息。
	 * @see #setType(String)
	 */
	public static final String TYPE_SYSPHOTO = "pic_sysphoto";
	
	/**
	 * <strong>弹出拍照或者相册发图</strong><br>
	 * 用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
	 * @see #setType(String)
	 */
	public static final String TYPE_PHOTO_OR_ALBUM = "pic_photo_or_album";
	
	/**
	 * <strong>弹出微信相册发图器</strong><br>
	 * 用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，
	 * 并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
	 * @see #setType(String)
	 */
	public static final String TYPE_PIC_WEIXIN = "pic_weixin";
	
	/**
	 * <strong>地理位置选择器</strong><br>
	 * 用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，
	 * 同时收起位置选择工具，随后可能会收到开发者下发的消息。
	 * @see #setType(String)
	 */
	public static final String TYPE_LOCATION_SELECT = "location_select";
	
	/**
	 * <strong>下拉消息（除文本消息）</strong><br>
	 * 用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给<strong>用户</strong>，
	 * 永久素材类型可以是图片、音频、视频、图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
	 * @see #setType(String)
	 */
	public static final String TYPE_MEDIA_ID = "media_id";
	
	/**
	 * <strong>跳转图文消息URL</strong><br>
	 * 用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，永久素材类型只支持图文消息。
	 * @see #setType(String)
	 */
	public static final String TYPE_VIEW_LIMITED = "view_limited";
	
	/**
	 * 菜单响应动作类型
	 */
	String type;
	
	/**
	 * 菜单名称
	 */
	String name;
	
	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节. click等点击类型必须
	 */
	String key;
	
	/**
	 * view类型必须。网页链接，用户点击菜单可打开链接，不超过256字节
	 */
	String url;
	
	/**
	 * media_id类型和view_limited类型必须，
	 * 调用新增永久素材接口返回的合法media_id
	 */
	String media_id;
	
	/**
	 * 二级菜单列表
	 */
	List<WechatMenu> sub_button;
	
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
		this.type = type;
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
	public List<WechatMenu> getSub_button(){
		return this.sub_button;
	}
	
	/**
	 * 设置二级菜单
	 * @see #sub_button
	 * @param sub_button
	 */
	public void setSub_button(List<WechatMenu> sub_button) {
		this.sub_button = sub_button;
	}

	/**
	 * 添加一个二级菜单
	 * @see #sub_button
	 * @param sub_button
	 */
	public void addSub_button(WechatMenu sub_button) {
		if(this.sub_button == null)
			this.sub_button = new ArrayList<WechatMenu>();
		this.sub_button.add(sub_button);
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
	 * @see #TYPE_MEDIA_ID
	 * @see #TYPE_VIEW_LIMITED
	 */
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	
}
