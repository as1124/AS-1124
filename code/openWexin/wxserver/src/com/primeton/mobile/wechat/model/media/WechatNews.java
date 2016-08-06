package com.primeton.mobile.wechat.model.media;

import com.alibaba.fastjson.annotation.JSONField;
import com.primeton.mobile.wechat.model.IWechatModel;

/**
 * 微信图文素材模型(新闻).
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatNews implements IWechatModel{

	/**
	 * 服务器上的media_id
	 */
	@JSONField
	String media_id;
	
	/**
	 * 更新时间
	 */
	@JSONField
	String update_time;
	
	/**
	 * 标题
	 */
	@JSONField
	String title;
	
	/**
	 * 图文消息的封面图片素材id(必须是永久素材mediaID)
	 */
	@JSONField
	String thumb_media_id;
	
	/**
	 * 作者
	 */
	@JSONField
	String author;
	
	/**
	 * 图文消息的摘要，仅当单图文消息时才显示摘要
	 */
	@JSONField
	String digest;
	
	/**
	 * 是否显示封面图片，
	 * <code>0</code> 代表<code>false</code>，
	 * <code>1</code> 代表<code>true</code>
	 */
	@JSONField
	int show_cover_pic;
	
	/**
	 * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
	 */
	@JSONField
	String content;
	
	/**
	 * 图文消息的原文地址，即点击"阅读原文"
	 */
	@JSONField
	String content_source_url;
	
	public WechatNews(){
		
	}

	/**
	 * @see #title
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @see #title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @see #thumb_media_id
	 * @return
	 */
	public String getThumb_media_id() {
		return thumb_media_id;
	}

	/**
	 * @see #thumb_media_id
	 * @param thumb_media_id
	 */
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	/**
	 * @see #author
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @see #author
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @see #digest
	 * @return
	 */
	public String getDigest() {
		return digest;
	}

	/**
	 * @see #digest
	 * @param digest
	 */
	public void setDigest(String digest) {
		this.digest = digest;
	}

	/**
	 * @see #show_cover_pic
	 * @return
	 */
	public int getShow_cover_pic() {
		return show_cover_pic;
	}

	/**
	 * @see #show_cover_pic
	 * @param show_cover_pic
	 */
	public void setShow_cover_pic(int show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}

	/**
	 * @see #content
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @see #content
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @see #content_source_url
	 * @return
	 */
	public String getContent_source_url() {
		return content_source_url;
	}

	/**
	 * @see #content_source_url
	 * @param content_source_url
	 */
	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

}
