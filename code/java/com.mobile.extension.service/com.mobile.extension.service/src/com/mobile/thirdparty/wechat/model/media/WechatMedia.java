package com.mobile.thirdparty.wechat.model.media;

import com.mobile.thirdparty.wechat.IWechatModel;

/**
 * 微信多媒体素材基础模型。可描述<code>image/voice/thumb</code>
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatMedia implements IWechatModel{

	/**
	 * 素材类型
	 * <li>image, 图片
	 * <li>voice, 语音
	 * <li>thumb, 视频与音乐格式的缩略图
	 */
	String type;
	
	/**
	 * 服务器上的素材ID
	 */
	String media_id;
	
	/**
	 * 缩略图文件时返回的json数据名称不叫media_id，而是thumb_media_id
	 */
	String thumb_media_id;
	
	/**
	 * 素材上传的时间戳
	 */
	String create_at;

	/**
	 * 新增的图片素材的图片URL；永久视频素材的下载url
	 */
	String url;
	
	/**
	 * 文件名称（图片）
	 */
	String name;
	
	/**
	 * 素材最后更新时间
	 */
	long update_time;
	
	public WechatMedia() {
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
	 * @param media_id
	 */
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
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
	 * @param create_at
	 */
	public void setCreate_at(String create_at) {
		this.create_at = create_at;
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
	 * @param thumb_media_id
	 */
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
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

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}
	
}
