package com.mobile.thirdparty.wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
import com.eos.system.annotation.Bizlet;
import com.mobile.thirdparty.access.AbstractAccessToken;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.wechat.model.WechatArticle;
import com.mobile.thirdparty.wechat.model.media.WechatMedia;
import com.mobile.thirdparty.wechat.model.media.WechatMediaList;
import com.mobile.thirdparty.wechat.model.media.WechatNewsList;

/**
 * WeChat 多媒体素材操作接口.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@Bizlet
public class MediaOperations {
	
	/**
	 * 上传临时图片素材(仅限本地文件)，临时素材在服务器上保存时间为3天。
	 * <li>图片（image）: 1M，支持bmp/png/jpeg/jpg/gif格式 
	 * @param token
	 * @param filePath 要上传文件的全路径名
	 * @return WechatMedia
	 */
	@Bizlet
	public WechatMedia uploadTempImage(AbstractAccessToken token, String filePath) {
		String result = this.uploadTempMedia(token, "image", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
        String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 上传临时语音素材(仅限本地文件)，临时素材在服务器上保存时间为3天
	 * <li>语音（voice）：2M，播放长度不超过60s，支持mp3/wma/wav/amr格式 
	 * @param token
	 * @param filePath 要上传文件的全路径名
	 * @return WechatMedia
	 */
	@Bizlet
	public WechatMedia uploadTempVoice(AbstractAccessToken token, String filePath) {
		String result = this.uploadTempMedia(token, "voice", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
        String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 上传临时缩略图素材(仅限本地文件)，临时素材在服务器上保存时间为3天。
	 * <li>缩略图（thumb）：64KB，支持JPG格式 
	 * @param token
	 * @param filePath 要上传文件的全路径名
	 * @return WechatMedia
	 */
	@Bizlet
	public WechatMedia uploadTempThumb(AbstractAccessToken token, String filePath) {
		String result = this.uploadTempMedia(token, "thumb", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
        String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 上传临时素材(仅限本地文件)，临时素材在服务器上保存时间为3天。
	 * <li>视频（video）：10MB，支持MP4格式
	 * @param token
	 * @param filePath 要上传文件的全路径名
	 * @return WechatMedia
	 */
	@Bizlet
	public WechatMedia uploadTempVideo(AbstractAccessToken token, String filePath) {
		String result = this.uploadTempMedia(token, "video", filePath);
		JSONObject returnResult = JSONObject.parseObject(result);
        String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}

	private String uploadTempMedia(AbstractAccessToken token, String type, String filePath) {
		String uri = "https://api.weixin.qq.com/cgi-bin/media/upload";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		queryStr.add(new BasicNameValuePair("type", type));
		String url = uri + "?" + URLEncodedUtils.format(queryStr, IWechatConstants.DEFAULT_CHARSET);
		return HttpExecuter.executeUploadFile(null, url, filePath, "media");
	}
	
	@Bizlet
	public void getTempImage(AbstractAccessToken token, String mediaID, String savePath){
		this.getTempMedia(token, mediaID, savePath);
	}
	
	@Bizlet
	public void getTempVoice(AbstractAccessToken token, String mediaID, String savePath){
		this.getTempMedia(token, mediaID, savePath);
	}
	
	@Bizlet
	public void getTempThumb(AbstractAccessToken token, String mediaID, String savePath){
		this.getTempMedia(token, mediaID, savePath);
	}
	
	/**
	 * @param token
	 * @param mediaID
	 * @return 视频下载链接
	 */
	@Bizlet
	public String getTempVideo(AbstractAccessToken token, String mediaID){
		String uri = "https://api.weixin.qq.com/cgi-bin/media/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		queryStr.add(new BasicNameValuePair("media_id", mediaID));
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject returnResult = JSONObject.parseObject(response);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return returnResult.getString("video_url");
		} else {
			System.err.println(IWechatConstants.MSG_TAG+response);
			return null;
		}
	}
	
	private void getTempMedia(AbstractAccessToken token, String mediaID, String savePath) {
		String uri = "https://api.weixin.qq.com/cgi-bin/media/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		queryStr.add(new BasicNameValuePair("media_id", mediaID));
		HttpResponse response = HttpExecuter.executeGetAsStream(uri, queryStr);
		try {
			InputStream in = response.getEntity().getContent();
			FileOutputStream out = new FileOutputStream(new File(savePath));
			byte[] buff = new byte[2048];
			int length = 0;
			while((length=in.read(buff)) >= 0){
				out.write(buff, 0, length);
			}
			in.close();
			out.close();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传图文消息内的图片获取URL,本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。
	 * 图片仅支持jpg/png格式，大小必须在1MB以下。
	 * @param token
	 * @param imgPath
	 * @return
	 */
	@Bizlet
	public String uploadNewsImg(AbstractAccessToken token, String imgPath){
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + token.getAccess_token();
        String result = HttpExecuter.executeUploadFile(null, url, imgPath, "media");
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result).getString("url");
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 上传图文消息获取素材media_id
	 * 
	 * @param token
	 * @param news
	 * @return media_id
	 */
	@Bizlet
	public String uploadNews(AbstractAccessToken token, WechatArticle[] news) {
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONArray articles = new JSONArray();
		articles.addAll(Arrays.asList(news));
		JSONObject postData = new JSONObject();
		postData.put("articles", articles);
		StringEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(url, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if (returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseObject(result).getString("media_id");
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 添加永久图文素材（新闻）
	 * @param token
	 * @param articles 图文消息
	 * @return media_id 永久图文消息的media_id
	 */
	@Bizlet
	public String uploadPermaentNews(AbstractAccessToken token, WechatArticle[] articles) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/add_news";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		JSONArray articlesStr = new JSONArray();
		articlesStr.addAll(Arrays.asList(articles));
		postData.put("articles", articlesStr);
        StringEntity reqEntity = new StringEntity(postData.toJSONString(), 
        		ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));  
        String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		JSONObject returnResult = JSONObject.parseObject(result);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return returnResult.getString("media_id");
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取永久图文素材（新闻）
	 * @param token
	 * @param mediaID 服务器上图文消息的media_id
	 * @return WechatNews[]
	 */
	@Bizlet
	public WechatArticle[] getPermaentNews(AbstractAccessToken token, String mediaID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringEntity reqEntity = new StringEntity(json.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
        String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		JSONObject returnResult = JSONObject.parseObject(result);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseArray(returnResult.getString("news_item"), WechatArticle.class)
					.toArray(new WechatArticle[]{});
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 上传永久图片素材.
	 * @param token
	 * @param filePath 素材的本地路径
	 * @return WechatMedia
	 */
	@Bizlet
	public WechatMedia uploadPermaentImage(AbstractAccessToken token, String filePath) {
		String result = uploadPermaentMedia(token, "image", filePath, null);
		JSONObject returnResult = JSONObject.parseObject(result);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 上传语音素材.
	 * @param token
	 * @param filePath 素材的本地路径
	 * @return WechatMedia
	 */
	@Bizlet
	public WechatMedia uploadPermaentVoice(AbstractAccessToken token, String filePath) {
		String result = uploadPermaentMedia(token, "voice", filePath, null);
		JSONObject returnResult = JSONObject.parseObject(result);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 上传永久缩略图素材.
	 * @param token
	 * @param filePath 素材的本地路径
	 * @return WechatMedia
	 */
	@Bizlet
	public WechatMedia uploadPermaentThumb(AbstractAccessToken token, String filePath) {
		String result = uploadPermaentMedia(token, "thumb", filePath, null);
		JSONObject returnResult = JSONObject.parseObject(result);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
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
	@Bizlet
	public WechatMedia uploadPermaentVideo(AbstractAccessToken token, String filePath, String title, String introduction) {
		WechatMedia video = null;
		String urlStr = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="
				+token.getAccess_token()+"&type=video";
		Map<String, ContentBody> contentParts = new HashMap<String, ContentBody>();
		JSONObject data = new JSONObject();
		data.put("title", title);
		data.put("introduction", introduction);
		ContentBody description = new StringBody(data.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		contentParts.put("description", description);
		String result = HttpExecuter.executeUploadFile(null, urlStr, filePath, "media", contentParts);
		JSONObject returnResult = JSONObject.parseObject(result);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			video = JSONObject.parseObject(result, WechatMedia.class);
			video.setType("video");
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
		}
		return video;
	}
	
	private String uploadPermaentMedia(AbstractAccessToken token, String type, String filePath, JSONObject otherData) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="
				+token.getAccess_token()+"&type="+type;
		return HttpExecuter.executeUploadFile(null, urlStr, filePath, "media");
	}
	
	/**
	 * 获取永久图片素材
	 * @param token
	 * @param mediaID 永久素材的media_id
	 * @param 素材要保存的路径
	 */
	@Bizlet
	public void getPermaentImage(AbstractAccessToken token, String mediaID, String savePath) {
		this.getPermaentMedia(token, mediaID, savePath);
	}
	
	/**
	 * 获取永久语音素材
	 * @param token
	 * @param mediaID 永久素材的media_id
	 * @param 素材要保存的路径
	 */
	@Bizlet
	public void getPermaentVoice(AbstractAccessToken token, String mediaID, String savePath) {
		this.getPermaentMedia(token, mediaID, savePath);
	}
	
	/**
	 * 获取缩略图素材
	 * @param token
	 * @param mediaID 永久素材的media_id
	 * @param 素材要保存的路径
	 */
	@Bizlet
	public void getPermaentThumb(AbstractAccessToken token, String mediaID, String savePath) {
		this.getPermaentMedia(token, mediaID, savePath);
	}
	
	/**
	 * 获取永久视频素材
	 * @param accessToken
	 * @param mediaID 永久素材的media_id
	 * @return WechatMeida
	 */
	@Bizlet
	public String getPermaentVideo(AbstractAccessToken token, String mediaID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringEntity requestEntity = new StringEntity(json.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject returnResult = JSONObject.parseObject(result);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return returnResult.getString("down_url");
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}

	private void getPermaentMedia(AbstractAccessToken token, String mediaID, String savePath) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringEntity requestEntity = new StringEntity(json.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		HttpResponse response = HttpExecuter.executePostAsStream(uri, queryStr, requestEntity);
		try {
			InputStream in = response.getEntity().getContent();
			FileOutputStream out = new FileOutputStream(new File(savePath));
			byte[] buff = new byte[2048];
			int length = 0;
			while((length=in.read(buff)) >= 0){
				out.write(buff, 0, length);
			}
			in.close();
			out.close();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除永久媒体素材
	 * @param token
	 * @param mediaID 永久素材的media_id
	 * @return
	 */
	@Bizlet
	public boolean deletePermaentMedia(AbstractAccessToken token, String mediaID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/del_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringEntity reqEntity = new StringEntity(json.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
        String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 更新永久图文素材
	 * @param token
	 * @param mediaID 永久图文素材的media_id
	 * @param index 需要更新的内容在素材中的索引位置, 第一篇为0
	 * @param newArticle
	 */
	@Bizlet
	public boolean updateWechatNews(AbstractAccessToken token, String mediaID, int index, WechatArticle newArticle) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/update_news";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		postData.put("media_id", mediaID);
		postData.put("index", index);
		postData.put("articles", newArticle);
		StringEntity reqEntity = new StringEntity(postData.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET)); 
        String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else{
			System.err.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 获取永久多媒体素材数量
	 * @param token
	 * @return Map 存放了分类统计的数量
	 */
	@Bizlet
	public HashMap<?, ?> getPermaentMediaCount(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, HashMap.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
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
	@Bizlet
	public WechatMediaList getPermaentMediaList(AbstractAccessToken token, String type, int offset, int count) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		postData.put("type", type);
		postData.put("offset", offset);
		postData.put("count", count);
		String result = HttpExecuter.executePostAsString(uri, queryStr, 
				new StringEntity(postData.toJSONString(), 
						ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET)));
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMediaList.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取图文素材列表
	 * @param accessToken
	 * @param offset a Integer start at 0
	 * @param count a Integer between 1 and 20
	 * @return
	 */
	@Bizlet
	public WechatNewsList getPermaentNewsList(String accessToken, int offset, int count) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("type", "news");
		postData.put("offset", offset);
		postData.put("count", count);
		String result = HttpExecuter.executePostAsString(uri, queryStr, 
				new StringEntity(postData.toJSONString(), 
						ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET)));
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatNewsList.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}

}