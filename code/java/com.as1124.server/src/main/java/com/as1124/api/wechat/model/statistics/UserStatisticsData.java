package com.as1124.api.wechat.model.statistics;

import com.alibaba.fastjson.JSONObject;
import com.as1124.api.wechat.model.IWechatModel;

/**
 * 用户分析统计数据
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class UserStatisticsData implements IWechatModel {

	/**
	 * 数据的日期
	 */
	private String ref_date = "2014-12-01";
	
	/**
	 * 用户渠道
	 * <p>
	 * <li>0-其他
	 * <li>1-公众号搜索
	 * <li>17-名片分享
	 * <li>30-扫码
	 * <li>35-搜索号码
	 * <li>39-查询微信公众号
	 * <li>43-图文右上角菜单
	 * <li>51-支付后关注
	 * <li>57-图文页内公众号名称
	 * <li>75-公众号文章广告
	 * <li>78-朋友圈广告
	 * </p>
	 */
	private int user_source = 0;
	
	/**
	 * 新增的用户数量
	 */
	private int new_user = 0;
	
	/**
	 * 取消关注的用户数量
	 */
	private int cancel_user = 0;
	
	/**
	 * 总用户数量
	 */
	private int cumulate_user = 0;
	
	public UserStatisticsData() {
		// default constructor
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
