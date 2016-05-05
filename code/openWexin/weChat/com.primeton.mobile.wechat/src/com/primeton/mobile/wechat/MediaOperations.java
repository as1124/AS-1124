package com.primeton.mobile.wechat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eos.system.annotation.Bizlet;
import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.media.WechatMedia;
import com.primeton.mobile.wechat.model.media.WechatMediaList;
import com.primeton.mobile.wechat.model.media.WechatNews;
import com.primeton.mobile.wechat.model.media.WechatNewsList;

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
	 * @param accessToken
	 * @param type 素材类型<code> image/video/voice/thumb</code>
	 * @param filePath 要上传文件的全路径名
	 * @return WechatMedia
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("uploadTemporaryMedia")
	public static WechatMedia uploadTemporaryMedia(String accessToken, String type, String filePath) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/media/upload";
		NameValuePair[] queryStr = new NameValuePair[2];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		queryStr[1] = new NameValuePair("type", type);
		File f = new File(filePath);
		FilePart filePart = new FilePart(f.getName(), f);
        String result = HttpExecuter.executePostAsString(uri, queryStr, new Part[]{filePart});
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		}
		else throw new WechatExceprion("[MediaOperations#uploadTemporaryMedia]"+result);
	}
	
	/**
	 * 获取临时媒体素材。
	 * <strong>请注意，视频文件不支持https下载，调用该接口需http协议。 </strong>
	 * @param accessToken
	 * @param mediaID 临时素材的media_id
	 * @param savePath 文件要保存的本地路径
	 * @return
	 */
	@Bizlet("getTemporaryMedia")
	public static void getTemporaryMedia(String accessToken, String mediaID, String savePath) {
		String uri = "https://api.weixin.qq.com/cgi-bin/media/get";
		NameValuePair[] queryStr = new NameValuePair[2];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		queryStr[1] = new NameValuePair("media_id", mediaID);
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
			if(out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 添加永久图文素材（新闻）
	 * @param accessToken
	 * @param articles 图文消息
	 * @return media_id 永久图文消息的media_id
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("addNews")
	public static String addNews(String accessToken, WechatNews[] articles) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/material/add_news";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		JSONObject postData = new JSONObject();
		JSONArray articlesStr = new JSONArray();
		articlesStr.addAll(Arrays.asList(articles));
		postData.put("articles", articlesStr);
        StringRequestEntity reqEntity = new StringRequestEntity(postData.toJSONString());  
        String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result).getString("media_id");
		}
		else throw new WechatExceprion("[MediaOperations#addNews]"+result);
	}
	
	/**
	 * 获取永久图文素材（新闻）
	 * @param accessToken
	 * @param mediaID 服务器上图文消息的media_id
	 * @return WechatNews[]
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	@Bizlet("getWechatNews")
	public static WechatNews[] getWechatNews(String accessToken, String mediaID) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringRequestEntity requestEntity = new StringRequestEntity(json.toJSONString(), IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
        String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("news_item"), WechatNews.class)
					.toArray(new WechatNews[]{});
		}
		else throw new WechatExceprion("[MediaOperations#getWechatNews]"+result);
	}
	
	/**
	 * 上传永久素材, 非视频素材.
	 * @param accessToken
	 * @param type <code>image/voice/thumb</code>
	 * @param filePath 素材的本地路径
	 * @return WechatMedia
	 * @throws IOException
	 */
	@Bizlet("uploadPermaentMedia")
	public static WechatMedia uploadPermaentMedia(String accessToken, String type, String filePath) throws IOException{
		WechatMedia media = null;
		String returnData = uploadPermaentMedia(accessToken, type, filePath, null);
		String returnCode = JSONObject.parseObject(returnData).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			media = JSONObject.parseObject(returnData, WechatMedia.class);
			media.setType(type);
		}
        return media;
	}
	
	/**
	 * 上传永久视频素材
	 * @param accessToken
	 * @param filePath 视频素材的本地路径
	 * @param title 视频素材标题
	 * @param introduction 视频描述
	 * @return WechatMedia
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	@Bizlet("uploadPermaentVedio")
	public static WechatMedia uploadPermaentVideo(String accessToken, String filePath, String title, String introduction) throws IOException{
		//ATTENTION 永久视频有问题
		WechatMedia video = null;
		JSONObject descripton = new JSONObject();
		descripton.put("title", title);
		descripton.put("introduction", introduction);
		String returnData = uploadPermaentMedia(accessToken, "video", filePath, descripton);
		String returnCode = JSONObject.parseObject(returnData).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			video = JSONObject.parseObject(returnData, WechatMedia.class);
			video.setType("video");
		}
		return video;
	}
	
	private static String uploadPermaentMedia(String accessToken, String type, String filePath, JSONObject otherData) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + accessToken;
		HttpURLConnection conn = null;
		// boundary就是request头和上传文件内容的分隔符
		String BOUNDARY = "----" + System.currentTimeMillis();
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			
			// text
			if ("video".equals(type)) {
				StringBuffer strBuf1 = new StringBuffer();
				strBuf1.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
				strBuf1.append("Content-Disposition: form-data; name=\"description\"\r\n\r\n");
				strBuf1.append("Content-Type:\"text/json\"\r\n\r\n");
				strBuf1.append(otherData.toJSONString());
				out.write(strBuf1.toString().getBytes("UTF-8"));
			}
			
			File file = new File(filePath);
			String contentType = new MimetypesFileTypeMap().getContentType(file);
			if (contentType == null || contentType.equals("")) {
				contentType = "application/octet-stream";
			}
			StringBuffer strBuf = new StringBuffer();
			strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
			strBuf.append("Content-Disposition: form-data; name=\"media\"; filename=\""
					+ file.getName() + "\"\r\n");
			strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
			out.write(strBuf.toString().getBytes("UTF-8"));
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据
			StringBuffer strBuf2 = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf2.append(line).append("\n");
			}
			reader.close();
			reader = null;
			String result = strBuf2.toString();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return "";
	}

	/**
	 * 获取永久媒体素材, 可以是<code>image/voice/thumb</code>
	 * @param accessToken
	 * @param mediaID 永久素材的media_id
	 * @param 素材要保存的路径
	 * @throws IOException 
	 */
	@Bizlet("getPermaentMedia")
	public static void getPermaentMedia(String accessToken, String mediaID, String savePath) throws IOException {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringRequestEntity requestEntity = new StringRequestEntity(json.toJSONString(), IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
        byte[] datas = HttpExecuter.executePost(uri, queryStr, requestEntity);
        OutputStream out = null;
        try {
        	out = new FileOutputStream(new File(savePath));
        	out.write(datas);
		} catch (Exception e) {
			
		}finally{
			if(out != null)
				out.close();
		}
	}
	
	/**
	 * 获取永久视频素材
	 * @param accessToken
	 * @param mediaID 永久素材的media_id
	 * @return WechatMeida
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	@Bizlet("getPermaentVedio")
	public static WechatMedia getPermaentVideo(String accessToken, String mediaID) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material";
		NameValuePair[] queryStr = new NameValuePair[2];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		queryStr[1] = new NameValuePair("media_id", mediaID);
        String result = HttpExecuter.executePostAsString(uri, queryStr, new StringRequestEntity(""));
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMedia.class);
		}
		else throw new WechatExceprion("[MediaOperations#getPermaentVedio]"+result);
	}
	
	/**
	 * 删除永久媒体素材
	 * @param accessToken
	 * @param mediaID 永久素材的media_id
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("deletePermaentMedia")
	public static boolean deletePermaentMedia(String accessToken, String mediaID) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/material/del_material";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		JSONObject json = new JSONObject();
		json.put("media_id", mediaID);
		StringRequestEntity requestEntity = new StringRequestEntity(json.toJSONString(), IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
        String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else {
			throw new WechatExceprion("[MediaOperations#deletePermaentMedia]"+result);
		}
	}
	
	/**
	 * 更新永久图文素材
	 * @param accessToken
	 * @param mediaID 永久图文素材的media_id
	 * @param index 需要更新的内容在素材中的索引位置, 第一篇为0
	 * @param newArticle
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	@Bizlet("updateWechatNews")
	public static boolean updateWechatNews(String accessToken, String mediaID, int index, WechatNews newArticle) throws IOException, WechatExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/update_news";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		JSONObject postData = new JSONObject();
		postData.put("media_id", mediaID);
		postData.put("index", index);
		postData.put("articles", newArticle);
        StringRequestEntity reqEntity = new StringRequestEntity(postData.toJSONString(), IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);  
        String result = HttpExecuter.executePostAsString(uri, queryStr, reqEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new WechatExceprion("[MediaOperations#updateWechatNews]"+result);
	}
	
	/**
	 * 获取永久多媒体素材数量
	 * @param accessToken
	 * @return Map 存放了分类统计的数量
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	@SuppressWarnings("rawtypes")
	@Bizlet("getPermaentMediaCount")
	public static HashMap getPermaentMediaCount(String accessToken) throws IOException, WechatExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, HashMap.class);
		}
		else throw new WechatExceprion("[MediaOperations#getPermaentMediaCount]"+result);
	}
	
	/**
	 * 根据素材类型获取素材列表
	 * @param accessToken
	 * @param type <code>image/video/voice</code>
	 * @param offset a Integer start at 0
	 * @param count a Integer between 1 and 20
	 * @return
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	@Bizlet("getPermaentMeidaList")
	public static WechatMediaList getPermaentMediaList(String accessToken, String type, int offset, int count) throws IOException, WechatExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		JSONObject postData = new JSONObject();
		postData.put("type", type);
		postData.put("offset", offset);
		postData.put("count", count);
		String result = HttpExecuter.executePostAsString(uri, queryStr, new StringRequestEntity(postData.toJSONString()));
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatMediaList.class);
		}
		else throw new WechatExceprion("[MediaOperations#getPermaentMeidaList]"+result);
	}
	
	/**
	 * 根据素材类型获取素材列表
	 * @param accessToken
	 * @param offset a Integer start at 0
	 * @param count a Integer between 1 and 20
	 * @return
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	@Bizlet("getPermaentMeidaList")
	public static WechatNewsList getPermaentNewsList(String accessToken, int offset, int count) throws IOException, WechatExceprion {
		String uri = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, accessToken);
		JSONObject postData = new JSONObject();
		postData.put("type", "news");
		postData.put("offset", offset);
		postData.put("count", count);
		String result = HttpExecuter.executePostAsString(uri, queryStr, new StringRequestEntity(postData.toJSONString()));
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
		if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, WechatNewsList.class);
		}
		else throw new WechatExceprion("[MediaOperations#getPermaentMeidaList]"+result);
	}

}