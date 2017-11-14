package com.mobile.thirdparty.wechat.model.statistics;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.wechat.model.IWechatModel;

/**
 * 用户分析统计数据
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class UserStatisticsData implements IWechatModel {

	/**
	 * 数据d日期
	 */
	String ref_date;
	
	/**
	 * 用户渠道, 0-其他，30-扫码，17-名片分享，35-搜索号码，39-查询微信公众号，
	 * 43-图文右上角菜单
	 */
	int user_source;
	
	/**
	 * 新增的用户数量
	 */
	int new_user;
	
	/**
	 * 取消关注的用户数量
	 */
	int cancel_user;
	
	/**
	 * 总用户数量
	 */
	int cumulate_user;
	
	public UserStatisticsData() {
	}
	
	public String toJSON() {
		return JSONObject.toJSONString(this);
	}

	public String getRef_date() {
		return ref_date;
	}

	public void setRef_date(String ref_date) {
		this.ref_date = ref_date;
	}

	public int getUser_source() {
		return user_source;
	}

	public void setUser_source(int user_source) {
		this.user_source = user_source;
	}

	public int getNew_user() {
		return new_user;
	}

	public void setNew_user(int new_user) {
		this.new_user = new_user;
	}

	public int getCancel_user() {
		return cancel_user;
	}

	public void setCancel_user(int cancel_user) {
		this.cancel_user = cancel_user;
	}

	public int getCumulate_user() {
		return cumulate_user;
	}

	public void setCumulate_user(int cumulate_user) {
		this.cumulate_user = cumulate_user;
	}

}
