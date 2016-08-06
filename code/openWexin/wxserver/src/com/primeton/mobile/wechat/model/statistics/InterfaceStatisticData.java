package com.primeton.mobile.wechat.model.statistics;

import com.alibaba.fastjson.annotation.JSONField;
import com.primeton.mobile.wechat.model.IWechatModel;

/**
 * 接口统计分析数据
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class InterfaceStatisticData implements IWechatModel {

	/**
	 * 数据的日期
	 */
	@JSONField
	String ref_date;
	
	/**
	 * 数据的小时
	 */
	@JSONField
	String ref_hour;
	
	/**
	 * 通过服务器配置地址获得消息后，被动回复用户消息的次数
	 */
	@JSONField
	int callback_count;
	
	/**
	 * 调用失败的次数
	 */
	@JSONField
	int fail_count;
	
	/**
	 * 总耗时。除以callback_count即为平均耗时
	 */
	@JSONField
	int total_time_cost;
	
	/**
	 * 最大耗时
	 */
	@JSONField
	int max_time_cost;
	
	public InterfaceStatisticData() {
	}
	
	public String toJSON() {
		return null;
	}

	public String getRef_date() {
		return ref_date;
	}

	public void setRef_date(String ref_date) {
		this.ref_date = ref_date;
	}

	public String getRef_hour() {
		return ref_hour;
	}

	public void setRef_hour(String ref_hour) {
		this.ref_hour = ref_hour;
	}

	public int getCallback_count() {
		return callback_count;
	}

	public void setCallback_count(int callback_count) {
		this.callback_count = callback_count;
	}

	public int getFail_count() {
		return fail_count;
	}

	public void setFail_count(int fail_count) {
		this.fail_count = fail_count;
	}

	public int getTotal_time_cost() {
		return total_time_cost;
	}

	public void setTotal_time_cost(int total_time_cost) {
		this.total_time_cost = total_time_cost;
	}

	public int getMax_time_cost() {
		return max_time_cost;
	}

	public void setMax_time_cost(int max_time_cost) {
		this.max_time_cost = max_time_cost;
	}
	
}
