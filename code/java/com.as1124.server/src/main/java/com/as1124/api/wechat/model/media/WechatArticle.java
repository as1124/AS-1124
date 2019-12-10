package com.as1124.api.wechat.model.media;

import com.as1124.api.wechat.model.IWechatModel;

/**
 * 微信图文素材模型(新闻).
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class WechatArticle implements IWechatModel {

	/**
	 * 服务器上的media_id
	 */
	private String media_id = "";

	/**
	 * 更新时间
	 */
	private String update_time = "";

	/**
	 * 标题
	 */
	private String title = "";

	/**
	 * 图文消息的封面图片素材id(必须是永久素材mediaID)
	 */
	private String thumb_media_id = "";

	/**
	 * 作者
	 */
	private String author = "";

	/**
	 * 图文消息的摘要，仅当单图文消息时才显示摘要
	 */
	private String digest = "";

	/**
	 * 是否显示封面图片，
	 * <code>0</code> 代表<code>false</code>，
	 * <code>1</code> 代表<code>true</code>
	 */
	private int show_cover_pic = 1;

	/**
	 * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
	 */
	private String content = "";

	/**
	 * 图文消息的原文地址，即点击"阅读原文"
	 */
	private String content_source_url = "";

	public WechatArticle() {
		// default constructor
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
	 * @param thumbMediaID
	 */
	public void setThumb_media_id(String thumbMediaID) {
		this.thumb_media_id = thumbMediaID;
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
	 * @param showCoverPic
	 */
	public void setShow_cover_pic(int showCoverPic) {
		this.show_cover_pic = showCoverPic;
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
	 * @param sourceURL
	 */
	public void setContent_source_url(String sourceURL) {
		this.content_source_url = sourceURL;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String mediaID) {
		this.media_id = mediaID;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String updateTime) {
		this.update_time = updateTime;
	}

}
