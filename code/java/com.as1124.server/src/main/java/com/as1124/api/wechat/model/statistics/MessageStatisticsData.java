package com.as1124.api.wechat.model.statistics;

import com.as1124.api.wechat.model.IWechatModel;

/**
 * 消息统计分析的数据
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class MessageStatisticsData implements IWechatModel {

	/**
	 * 数据的日期
	 */
	String ref_date;
	
	/**
	 * 数据的小时
	 */
	String ref_hour;
	
	/**
	 * 消息类型，1-文字，2-图片，3-语音，4-视频，6-第三方应用消息
	 */
	int msg_type;
	
	/**
	 * 向公众号发送了消息的用户数量
	 */
	int msg_user;
	
	/**
	 * 向公众号发送的消息数量
	 */
	int msg_count;
	
	/**
	 * 当日发送消息量分布区间
	 */
	int count_interval;
	
	/**
	 * 图文页的阅读次数
	 */
	int int_page_read_count;
	
	/**
	 * 原文页的阅读人数
	 */
	int ori_page_read_user;
	
	public MessageStatisticsData() {
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

	public int getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}

	public int getMsg_user() {
		return msg_user;
	}

	public void setMsg_user(int msg_user) {
		this.msg_user = msg_user;
	}

	public int getMsg_count() {
		return msg_count;
	}

	public void setMsg_count(int msg_count) {
		this.msg_count = msg_count;
	}

	public int getCount_interval() {
		return count_interval;
	}

	public void setCount_interval(int count_interval) {
		this.count_interval = count_interval;
	}

	public int getInt_page_read_count() {
		return int_page_read_count;
	}

	public void setInt_page_read_count(int int_page_read_count) {
		this.int_page_read_count = int_page_read_count;
	}

	public int getOri_page_read_user() {
		return ori_page_read_user;
	}

	public void setOri_page_read_user(int ori_page_read_user) {
		this.ori_page_read_user = ori_page_read_user;
	}
	
}
