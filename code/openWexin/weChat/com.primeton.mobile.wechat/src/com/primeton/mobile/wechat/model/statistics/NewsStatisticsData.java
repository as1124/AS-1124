package com.primeton.mobile.wechat.model.statistics;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.primeton.mobile.wechat.model.IWechatModel;

/**
 * 图文分析统计数据
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@JSONType
public class NewsStatisticsData implements IWechatModel {
	/**
	 * 数据的日期
	 */
	@JSONField
	String ref_date;
	
	/**
	 * 数据的小时
	 */
	@JSONField
	int ref_hour;
	
	/**
	 * 统计日期
	 */
	@JSONField
	String stat_date;
	
	/**
	 * 图文消息的message_id和index组合而成.
	 * eg: 12003_3
	 */
	@JSONField
	String msgid;
	
	/**
	 * 图文消息的标题
	 */
	@JSONField
	String title;
	
	/**
	 * 图文页的阅读人数
	 */
	@JSONField
	int int_page_read_user;
	
	/**
	 * 图文页的阅读次数
	 */
	@JSONField
	int int_page_read_count;
	
	/**
	 * 原文页的阅读人数
	 */
	@JSONField
	int ori_page_read_user;
	
	/**
	 * 原文页的阅读次数
	 */
	@JSONField
	int ori_page_read_count;
	
	/**
	 * 分享场景. 1-好友转发，2-代表朋友圈，3-代表腾讯微博，255-代表其他
	 */
	@JSONField
	int share_scene;
	
	/**
	 * 分享的人数
	 */
	@JSONField
	int share_user;
	
	/**
	 * 分享的次数
	 */
	@JSONField
	int share_count;
	
	/**
	 * 收藏的人数
	 */
	@JSONField
	int add_to_fav_user;
	
	/**
	 * 收藏的次数
	 */
	@JSONField
	int add_to_fav_count;
	
	/**
	 * 送达人数
	 */
	@JSONField
	int target_user;
	
	@JSONField
	List<NewsStatisticsData> details;
	
	public NewsStatisticsData() {
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

	public int getRef_hour() {
		return ref_hour;
	}

	public void setRef_hour(int ref_hour) {
		this.ref_hour = ref_hour;
	}

	public String getStat_date() {
		return stat_date;
	}

	public void setStat_date(String stat_date) {
		this.stat_date = stat_date;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getInt_page_read_user() {
		return int_page_read_user;
	}

	public void setInt_page_read_user(int int_page_read_user) {
		this.int_page_read_user = int_page_read_user;
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

	public int getOri_page_read_count() {
		return ori_page_read_count;
	}

	public void setOri_page_read_count(int ori_page_read_count) {
		this.ori_page_read_count = ori_page_read_count;
	}

	public int getShare_scene() {
		return share_scene;
	}

	public void setShare_scene(int share_scene) {
		this.share_scene = share_scene;
	}

	public int getShare_user() {
		return share_user;
	}

	public void setShare_user(int share_user) {
		this.share_user = share_user;
	}

	public int getShare_count() {
		return share_count;
	}

	public void setShare_count(int share_count) {
		this.share_count = share_count;
	}

	public int getAdd_to_fav_user() {
		return add_to_fav_user;
	}

	public void setAdd_to_fav_user(int add_to_fav_user) {
		this.add_to_fav_user = add_to_fav_user;
	}

	public int getAdd_to_fav_count() {
		return add_to_fav_count;
	}

	public void setAdd_to_fav_count(int add_to_fav_count) {
		this.add_to_fav_count = add_to_fav_count;
	}

	public int getTarget_user() {
		return target_user;
	}

	public void setTarget_user(int target_user) {
		this.target_user = target_user;
	}

	public List<NewsStatisticsData> getDetail() {
		return details;
	}

	public void setDetail(List<NewsStatisticsData> details) {
		this.details = details;
	}

}
