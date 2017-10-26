package com.mobile.thirdparty.wechat.model.menu;

/**
 * 个性化菜单模型。
 * 添加个性化菜单<p>通过该接口，让公众号的不同用户群体看到不一样的自定义菜单。
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
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatConditionMenu {

	/**
	 * 菜单
	 */
	WechatMenu[] button;
	
	/**
	 * 菜单匹配规则
	 */
	WechatMenuRuler matchrule;
	
	/**
	 * 个性化菜单的id
	 */
	String menuid;
	
	public WechatConditionMenu() {
	}

	/**
	 * 
	 * @see #button
	 */
	public WechatMenu[] getButton() {
		return button;
	}

	/**
	 * @param button
	 * @see #button
	 */
	public void setButton(WechatMenu[] button) {
		this.button = button;
	}

	/**
	 * 获取个性化菜单的匹配规则
	 * @return
	 */
	public WechatMenuRuler getMatchrule() {
		return matchrule;
	}

	/**
	 * 设置个性化菜单的匹配规则
	 * @param matchrule
	 */
	public void setMatchrule(WechatMenuRuler matchrule) {
		this.matchrule = matchrule;
	}

	/**
	 * 获取个性化菜单的menuid
	 * @see #menuid
	 * @return
	 */
	public String getMenuid() {
		return menuid;
	}

	/**
	 * @see #menuid
	 * @param menuid
	 */
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

}
