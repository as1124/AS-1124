package com.primeton.mobile.thirdparty.wechat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.thirdparty.access.AbstractAccessToken;
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.primeton.mobile.thirdparty.wechat.model.WechatArticle;
import com.primeton.mobile.thirdparty.wechat.model.media.WechatMedia;
import com.primeton.mobile.thirdparty.wechat.model.media.WechatMediaList;
import com.primeton.mobile.thirdparty.wechat.model.media.WechatNewsList;

/**
 * WeChat 多媒体素材操作接口.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class MediaOperations {
	
	/**
	 * 上传临时素材(仅限本地文件)，临时素材在服务器上保存时间为3天。素材大小、格式限制如下
	 * <li>图片（image）: 1M，支持bmp/png/jpeg/jpg/gif格式 
	 * <li>语音（voice）：2M，播放长度不超过60s，支持mp3/wma/wav/amr格式 
	 * <li>视频（video）：10MB，支持MP4格式
	 * <li>缩略图（thumb）：64KB，支持JPG格式 
	 * @param token
	 * @param type 素材类型<code> image/video/voice/thumb</code>
	 * @param filePath 要上传文件的全路径名
	 * @return WechatMedia
	 */
	public WechatMedia uploadTempMedia(AbstractAccessToken token, String type, String filePath) {
		String uri = "https://api.weixin.qq.com/cgi-bin/media/upload";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		queryStr.add(new BasicNameValuePair("type", type));
		String url = uri + "?" + URLEncodedUtils.format(queryStr, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executeUploadFile(null, url, filePath, "media");
        JSONObject returnResult = JSONObject.parseObject(result);
        String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取临时媒体素材。
	 * <strong>请注意，视频文件不支持https下载，调用该接口需http协议。 </strong>
	 * @param token
	 * @param mediaID 临时素材的media_id
	 * @param savePath 文件要保存的本地路径
	 * @return
	 */
	public void getTempMedia(AbstractAccessToken token, String mediaID, String savePath) {
		String uri = "https://api.weixin.qq.com/cgi-bin/media/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		queryStr.add(new BasicNameValuePair("media_id", mediaID));
		byte[] datas = HttpExecuter.executeGet(uri, queryStr);
		File file = new File(savePath);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(datas);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 上传图文消息内的图片获取URL,本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。
	 * 图片仅支持jpg/png格式，大小必须在1MB以下。
	 * @param token
	 * @param imgPath
	 * @return
	 */
	public String uploadNewsImg(AbstractAccessToken token, String imgPath){
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + token.getAccess_token();
        String result = HttpExecuter.executeUploadFile(null, url, imgPath, "media");
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result).getString("url");
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
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
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 添加永久图文素材（新闻）
	 * @param token
	 * @param articles 图文消息
	 * @return media_id 永久图文消息的media_id
	 */
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
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取永久图文素材（新闻）
	 * @param token
	 * @param mediaID 服务器上图文消息的media_id
	 * @return WechatNews[]
	 */
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
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 上传永久图片素材.
	 * @param token
	 * @param filePath 素材的本地路径
	 * @return WechatMedia
	 */
	public WechatMedia uploadPermaentImage(AbstractAccessToken token, String filePath) {
		String result = uploadPermaentMedia(token, "image", filePath, null);
		JSONObject returnResult = JSONObject.parseObject(result);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 上传语音素材.
	 * @param token
	 * @param filePath 素材的本地路径
	 * @return WechatMedia
	 */
	public WechatMedia uploadPermaentVoice(AbstractAccessToken token, String filePath) {
		String result = uploadPermaentMedia(token, "voice", filePath, null);
		JSONObject returnResult = JSONObject.parseObject(result);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 上传永久缩略图素材.
	 * @param token
	 * @param filePath 素材的本地路径
	 * @return WechatMedia
	 */
	public WechatMedia uploadPermaentThumb(AbstractAccessToken token, String filePath) {
		String result = uploadPermaentMedia(token, "thumb", filePath, null);
		JSONObject returnResult = JSONObject.parseObject(result);
		String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
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
			System.out.println(IWechatConstants.MSG_TAG+result);
		}
		return video;
	}
	
	private String uploadPermaentMedia(AbstractAccessToken token, String type, String filePath, JSONObject otherData) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="
				+token.getAccess_token()+"&type="+type;
		return HttpExecuter.executeUploadFile(null, urlStr, filePath, "media");
	}

	/**
	 * 获取永久媒体素材, 可以是<code>image/voice/thumb</code>
	 * @param accessToken
	 * @param mediaID 永久素材的media_id
	 * @param 素材要保存的路径
	 * @throws IOException 
	 */
	public void getPermaentMedia(String accessToken, String mediaID, String savePath) {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		
	}
	
	/**
	 * 获取永久视频素材
	 * @param accessToken
	 * @param mediaID 永久素材的media_id
	 * @return WechatMeida
	 * @throws IOException 
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatMedia getPermaentVideo(String accessToken, String mediaID) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		queryStr.add(new BasicNameValuePair("media_id", mediaID));
        String result = HttpExecuter.executePostAsString(uri, queryStr, new StringEntity(""));
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		}
		else throw new ThirdPartyRequestExceprion("[MediaOperations#getPermaentVedio]"+result);
	}
	
	/**
	 * 删除永久媒体素材
	 * @param accessToken
	 * @param mediaID 永久素材的media_id
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static boolean deletePermaentMedia(String accessToken, String mediaID) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/material/del_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringEntity reqEntity = new StringEntity(json.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
        String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else {
			throw new ThirdPartyRequestExceprion("[MediaOperations#deletePermaentMedia]"+result);
		}
	}
	
	/**
	 * 更新永久图文素材
	 * @param accessToken
	 * @param mediaID 永久图文素材的media_id
	 * @param index 需要更新的内容在素材中的索引位置, 第一篇为0
	 * @param newArticle
	 * @throws IOException 
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static boolean updateWechatNews(String accessToken, String mediaID, int index, WechatArticle newArticle) throws IOException, ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/update_news";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("media_id", mediaID);
		postData.put("index", index);
		postData.put("articles", newArticle);
		StringEntity reqEntity = new StringEntity(postData.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET)); 
        String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new ThirdPartyRequestExceprion("[MediaOperations#updateWechatNews]"+result);
	}
	
	/**
	 * 获取永久多媒体素材数量
	 * @param accessToken
	 * @return Map 存放了分类统计的数量
	 * @throws IOException 
	 * @throws ThirdPartyRequestExceprion 
	 */
	@SuppressWarnings("rawtypes")
	public static HashMap getPermaentMediaCount(String accessToken) throws IOException, ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, HashMap.class);
		}
		else throw new ThirdPartyRequestExceprion("[MediaOperations#getPermaentMediaCount]"+result);
	}
	
	/**
	 * 根据素材类型获取素材列表
	 * @param accessToken
	 * @param type <code>image/video/voice</code>
	 * @param offset a Integer start at 0
	 * @param count a Integer between 1 and 20
	 * @return
	 * @throws IOException 
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatMediaList getPermaentMediaList(String accessToken, String type, int offset, int count) throws IOException, ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("type", type);
		postData.put("offset", offset);
		postData.put("count", count);
		String result = HttpExecuter.executePostAsString(uri, queryStr, new StringEntity(postData.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET)));
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMediaList.class);
		}
		else throw new ThirdPartyRequestExceprion("[MediaOperations#getPermaentMeidaList]"+result);
	}
	
	/**
	 * 根据素材类型获取素材列表
	 * @param accessToken
	 * @param offset a Integer start at 0
	 * @param count a Integer between 1 and 20
	 * @return
	 * @throws IOException 
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatNewsList getPermaentNewsList(String accessToken, int offset, int count) throws IOException, ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("type", "news");
		postData.put("offset", offset);
		postData.put("count", count);
		String result = HttpExecuter.executePostAsString(uri, queryStr, new StringEntity(postData.toJSONString(), ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET)));
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatNewsList.class);
		}
		else throw new ThirdPartyRequestExceprion("[MediaOperations#getPermaentMeidaList]"+result);
	}

}