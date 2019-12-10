package com.as1124.api.wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.as1124.api.access.AbstractAccessToken;
import com.as1124.api.access.HttpExecuter;
import com.as1124.api.wechat.model.media.WechatArticle;
import com.as1124.api.wechat.model.media.WechatMedia;
import com.as1124.api.wechat.model.media.WechatMediaList;
import com.as1124.api.wechat.model.media.WechatNewsList;
import com.as1124.common.util.LoggerUtil;

/**
 * WeChat 多媒体素材操作接口.
 * 
 *
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public class MediaOperations {

	static Logger logger = LoggerUtil.getLogger(MediaOperations.class);

	private MediaOperations() {
	}

	/**
	 * 上传临时图片素材(仅限本地文件)，临时素材在服务器上保存时间为3天。
	 * <li>图片（image）: 2M，支持PNG/JPEG/JPG/GIF格式
	 * 
	 * @param token
	 * @param filePath 要上传文件的本地路径
	 * @return WechatMedia
	 */
	public static WechatMedia uploadTempImage(AbstractAccessToken token, String filePath) {
		String result = uploadTempMedia(token, "image", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

	/**
	 * 上传临时语音素材(仅限本地文件)，临时素材在服务器上保存时间为3天
	 * <li>语音（voice）：2M，播放长度不超过60s，支持MP3/AMR格式 
	 * 
	 * @param token
	 * @param filePath 要上传文件的本地路径
	 * @return WechatMedia
	 */

	public static WechatMedia uploadTempVoice(AbstractAccessToken token, String filePath) {
		String result = uploadTempMedia(token, "voice", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

	/**
	 * 上传临时缩略图素材(仅限本地文件)，临时素材在服务器上保存时间为3天。
	 * <li>缩略图（thumb）：64KB，支持JPG格式 
	 * 
	 * @param token
	 * @param filePath 要上传文件的本地路ing
	 * @return WechatMedia
	 */

	public static WechatMedia uploadTempThumb(AbstractAccessToken token, String filePath) {
		String result = uploadTempMedia(token, "thumb", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

	/**
	 * 上传临时素材(仅限本地文件)，临时素材在服务器上保存时间为3天。
	 * <li>视频（video）：10MB，支持MP4格式
	 * 
	 * @param token
	 * @param filePath 要上传文件的全路径名
	 * @return WechatMedia
	 */

	public static WechatMedia uploadTempVideo(AbstractAccessToken token, String filePath) {
		String result = uploadTempMedia(token, "video", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

	private static String uploadTempMedia(AbstractAccessToken token, String type, String filePath) {
		String uri = "https://api.weixin.qq.com/cgi-bin/media/upload";

		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		queryStr.add(new BasicNameValuePair("type", type));
		String url = uri + "?" + URLEncodedUtils.format(queryStr, WechatConstants.CHARSET_UTF8);
		return HttpExecuter.executeUploadFile(null, url, filePath, "media");
	}

	public static void getTempImage(AbstractAccessToken token, String mediaID, String savePath) throws IOException {
		getTempMedia(token, mediaID, savePath);
	}

	// ATTENTION 这里有一种通过JSSDK上传的语音素材没处理

	public static void getTempVoice(AbstractAccessToken token, String mediaID, String savePath) throws IOException {
		getTempMedia(token, mediaID, savePath);
	}

	public static void getTempThumb(AbstractAccessToken token, String mediaID, String savePath) throws IOException {
		getTempMedia(token, mediaID, savePath);
	}

	/**
	 * @param token
	 * @param mediaID
	 * @return 视频下载链接
	 */
	public static String getTempVideo(AbstractAccessToken token, String mediaID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/media/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		queryStr.add(new BasicNameValuePair("media_id", mediaID));
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject returnResult = JSONObject.parseObject(response);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return returnResult.getString("video_url");
		} else {
			logger.log(Level.SEVERE, response);
			return "";
		}
	}

	private static void getTempMedia(AbstractAccessToken token, String mediaID, String savePath) throws IOException {
		String uri = "https://api.weixin.qq.com/cgi-bin/media/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		queryStr.add(new BasicNameValuePair("media_id", mediaID));
		HttpResponse response = HttpExecuter.executeGetAsStream(uri, queryStr);
		FileOutputStream out = null;
		try (InputStream in = response.getEntity().getContent();) {
			out = new FileOutputStream(new File(savePath));
			byte[] buff = new byte[2048];
			int length = 0;
			while ((length = in.read(buff)) != -1) {
				out.write(buff, 0, length);
			}
			in.close();
		} catch (IllegalStateException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * 上传图文消息内的图片获取URL，图文消息的具体内容中，微信后台将过滤外部的图片链接。 
	 * 图片显示时进行CDN加速，本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。
	 * 图片仅支持JPG/PNG格式，大小必须在1MB以下。
	 * 
	 * @param token
	 * @param imgPath
	 * @return 图片的http链接
	 */
	public static String uploadImgForNews(AbstractAccessToken token, String imgPath) {
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + token.getAccessToken();
		String result = HttpExecuter.executeUploadFile(null, url, imgPath, "media");
		JSONObject resultJSON = JSONObject.parseObject(result);
		if (resultJSON.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return resultJSON.getString("url");
		} else {
			logger.log(Level.SEVERE, result);
			return "";
		}
	}

	/**
	 * 添加永久图文素材（新闻）
	 * 
	 * @param token
	 * @param articles 图文消息
	 * @return media_id 永久图文消息的media_id
	 */
	public static String uploadPermaentNews(AbstractAccessToken token, WechatArticle[] articles) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/add_news";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		JSONArray articlesStr = new JSONArray();
		articlesStr.addAll(Arrays.asList(articles));
		postData.put("articles", articlesStr);
		StringEntity reqEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return returnResult.getString("media_id");
		} else {
			logger.log(Level.SEVERE, result);
			return "";
		}
	}

	/**
	 * 上传永久图片素材.
	 * @param token
	 * @param filePath 素材的本地路径
	 * @return WechatMedia
	 */
	public static WechatMedia uploadPermaentImage(AbstractAccessToken token, String filePath) {
		String result = uploadPermaentMedia(token, "image", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

	/**
	 * 上传语音素材.
	 * @param token
	 * @param filePath 素材的本地路径
	 * @return WechatMedia
	 */
	public static WechatMedia uploadPermaentVoice(AbstractAccessToken token, String filePath) {
		String result = uploadPermaentMedia(token, "voice", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

	/**
	 * 上传永久缩略图素材.
	 * @param token
	 * @param filePath 素材的本地路径
	 * @return WechatMedia
	 */

	public static WechatMedia uploadPermaentThumb(AbstractAccessToken token, String filePath) {
		String result = uploadPermaentMedia(token, "thumb", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

	/**
	 * 上传永久视频素材
	 * @param token
	 * @param filePath 视频素材的本地路径
	 * @param title 视频素材标题
	 * @param introduction 视频描述
	 * @return WechatMedia
	 */
	public static WechatMedia uploadPermaentVideo(AbstractAccessToken token, String filePath, String title,
			String introduction) {
		WechatMedia video = null;
		String urlStr = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + token.getAccessToken()
				+ "&type=video";
		Map<String, ContentBody> contentParts = new HashMap<>();
		JSONObject data = new JSONObject();
		data.put("title", title);
		data.put("introduction", introduction);
		ContentBody description = new StringBody(data.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		contentParts.put("description", description);
		String result = HttpExecuter.executeUploadFile(null, urlStr, filePath, "media", contentParts);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			video = JSONObject.parseObject(result, WechatMedia.class);
			video.setType("video");
		} else {
			logger.log(Level.SEVERE, result);
		}
		return video;
	}

	private static String uploadPermaentMedia(AbstractAccessToken token, String type, String filePath) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + token.getAccessToken()
				+ "&type=" + type;
		return HttpExecuter.executeUploadFile(null, urlStr, filePath, "media");
	}

	/**
	 * 获取永久图片素材
	 * @param token
	 * @param mediaID 永久素材的 media_id
	 * @param 素材要保存的路径
	 * @throws IOException 
	 */
	public static void getPermaentImage(AbstractAccessToken token, String mediaID, String savePath) throws IOException {
		getPermaentMedia(token, mediaID, savePath);
	}

	/**
	 * 获取永久语音素材
	 * @param token
	 * @param mediaID 永久素材的 media_id
	 * @param 素材要保存的路径
	 * @throws IOException 
	 */
	public static void getPermaentVoice(AbstractAccessToken token, String mediaID, String savePath) throws IOException {
		getPermaentMedia(token, mediaID, savePath);
	}

	/**
	 * 获取缩略图素材
	 * @param token
	 * @param mediaID 永久素材的 media_id
	 * @param 素材要保存的路径
	 * @throws IOException 
	 */

	public static void getPermaentThumb(AbstractAccessToken token, String mediaID, String savePath) throws IOException {
		getPermaentMedia(token, mediaID, savePath);
	}

	/**
	 * 获取永久图文素材（新闻）
	 * @param token
	 * @param mediaID 服务器上图文消息的media_id
	 * @return WechatNews[]
	 */
	public static WechatArticle[] getPermaentNews(AbstractAccessToken token, String mediaID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringEntity reqEntity = new StringEntity(json.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseArray(returnResult.getString("news_item"), WechatArticle.class)
					.toArray(new WechatArticle[] {});
		} else {
			logger.log(Level.SEVERE, result);
			return new WechatArticle[0];
		}
	}

	/**
	 * 获取永久视频素材
	 * @param accessToken
	 * @param mediaID 永久素材的 media_id
	 * @return 视频素材的信息
	 */
	public static WechatMedia getPermaentVideo(AbstractAccessToken token, String mediaID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringEntity requestEntity = new StringEntity(json.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject returnResult = JSONObject.parseObject(result);
		int returnCode = returnResult.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			WechatMedia videoInfo = new WechatMedia();
			videoInfo.setUrl(returnResult.getString("down_url"));
			videoInfo.setType("video");
			videoInfo.setTitle(returnResult.getString("title"));
			videoInfo.setDescription(returnResult.getString("description"));
			return videoInfo;
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

	private static void getPermaentMedia(AbstractAccessToken token, String mediaID, String savePath)
			throws IOException {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringEntity requestEntity = new StringEntity(json.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		HttpResponse response = HttpExecuter.executePostAsStream(uri, queryStr, requestEntity);
		FileOutputStream out = null;
		try (InputStream in = response.getEntity().getContent();) {
			out = new FileOutputStream(new File(savePath));
			byte[] buff = new byte[2048];
			int length = 0;
			while ((length = in.read(buff)) >= 0) {
				out.write(buff, 0, length);
			}
			in.close();
		} catch (IllegalStateException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * 删除永久媒体素材
	 * @param token
	 * @param mediaID 永久素材的media_id
	 * @return
	 */
	public static boolean deletePermaentMedia(AbstractAccessToken token, String mediaID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/del_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringEntity reqEntity = new StringEntity(json.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 更新永久图文素材
	 * @param token
	 * @param mediaID 永久图文素材的 media_id
	 * @param index 需要更新的内容在素材中的索引位置, 第一篇为0
	 * @param newArticle
	 */
	public static boolean updateWechatNews(AbstractAccessToken token, String mediaID, int index,
			WechatArticle newArticle) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/update_news";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("media_id", mediaID);
		postData.put("index", index);
		postData.put("articles", newArticle);
		StringEntity reqEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			;
			return false;
		}
	}

	/**
	 * 获取永久多媒体素材数量
	 * @param token
	 * @return Map 存放了分类统计的数量
	 */
	@SuppressWarnings("rawtypes")
	public static Map getPermaentMediaCount(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, HashMap.class);
		} else {
			logger.log(Level.SEVERE, result);
			return Collections.emptyMap();
		}
	}

	/**
	 * 根据素材类型获取素材列表
	 * @param token
	 * @param type <code>image/video/voice</code>
	 * @param offset a Integer start at 0
	 * @param count a Integer between 1 and 20
	 * @return 
	 */
	public static WechatMediaList getPermaentMediaList(AbstractAccessToken token, String type, int offset, int count) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("type", type);
		postData.put("offset", offset);
		postData.put("count", count);
		String result = HttpExecuter.executePostAsString(uri, queryStr, new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8)));
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, WechatMediaList.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new WechatMediaList();
		}
	}

	/**
	 * 获取图文素材列表
	 * @param accessToken
	 * @param offset a Integer start at 0
	 * @param count a Integer between 1 and 20
	 * @return
	 */
	public static WechatNewsList getPermaentNewsList(String accessToken, int offset, int count) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("type", "news");
		postData.put("offset", offset);
		postData.put("count", count);
		String result = HttpExecuter.executePostAsString(uri, queryStr, new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8)));
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, WechatNewsList.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new WechatNewsList();
		}
	}

}