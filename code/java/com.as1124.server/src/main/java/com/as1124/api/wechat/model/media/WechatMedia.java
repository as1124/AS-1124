package com.as1124.api.wechat.model.media;

import com.as1124.api.wechat.model.IWechatModel;

/**
 * 微信多媒体素材基础模型。可描述<code>image/voice/thumb</code>
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class WechatMedia implements IWechatModel {

	/**
	 * 素材类型
	 * <li>image, 图片
	 * <li>voice, 语音
	 * <li>thumb, 视频与音乐格式的缩略图
	 */
	private String type = "";

	/**
	 * 服务器上的素材ID
	 */
	private String media_id = "";

	/**
	 * 缩略图文件时返回的json数据名称不叫media_id，而是thumb_media_id
	 */
	private String thumb_media_id = "";

	/**
	 * 素材上传的时间戳
	 */
	private String create_at = "";

	/**
	 * 新增的图片素材的图片URL；永久视频素材的下载URL
	 */
	private String url = "";

	/**
	 * 文件名称（图片）
	 */
	private String name = "";

	/**
	 * 素材最后更新时间
	 */
	private long update_time = 0;

	private String title = "";

	private String description = "";

	public WechatMedia() {
		// default constructor
	}

	/**
	 * @see #type
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @see #type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @see #media_id
	 * @return
	 */
	public String getMedia_id() {
		return media_id;
	}

	/**
	 * @see #media_id
	 * @param mediaID
	 */
	public void setMedia_id(String mediaID) {
		this.media_id = mediaID;
	}

	/**
	 * @see #create_at
	 * @return
	 */
	public String getCreate_at() {
		return create_at;
	}

	/**
	 * @see #create_at
	 * @param createAt
	 */
	public void setCreate_at(String createAt) {
		this.create_at = createAt;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long updateTime) {
		this.update_time = updateTime;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
