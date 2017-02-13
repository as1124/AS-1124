package com.primeton.mobile.thirdparty.wechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.thirdparty.access.AbstractAccessToken;
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.wechat.model.user.SubscribedCountInfo;
import com.primeton.mobile.thirdparty.wechat.model.user.UserBlackList;
import com.primeton.mobile.thirdparty.wechat.model.user.WechatGroup;
import com.primeton.mobile.thirdparty.wechat.model.user.WechatUserInfo;

/**
 * WeChat 用户相关操作API.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class UserOperations {
	
	/**
	 * 获取用户基本信息
	 * @param accessToken
	 * @param openID 用户对该公众号的唯一标识
	 * @return WechatUserInfo
	 */
	public WechatUserInfo getUserInfo(AbstractAccessToken token, String openID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		queryStr.add(new BasicNameValuePair("openid", openID));
		queryStr.add(new BasicNameValuePair("lang", Locale.getDefault().toString()));
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(response);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(response, WechatUserInfo.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+response);
			return null;
		}
	}
	
	/**
	 * 批量获取用户信息
	 * @param token 接口调用凭证
	 * @param users <code>Map&lt;openID, lang></code>
	 * @return
	 */
	public List<WechatUserInfo> getUsersInfo(AbstractAccessToken token, Map<String, String> users){
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postdata = new JSONObject();
		JSONArray userList = new JSONArray();
		Iterator<String> it = users.keySet().iterator();
		while(it.hasNext()){
			JSONObject json = new JSONObject();
			String openid = it.next();
			json.put(openid, users.get(openid));
			userList.add(json);
		}
		
		postdata.put("user_list", userList);
		HttpEntity requestEntity = new StringEntity(postdata.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject returnResult = JSONObject.parseObject(result);
        String returnCode = returnResult.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	
			return JSONObject.parseArray(returnResult.getString("user_info_list"), WechatUserInfo.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 创建用户分组标签
	 * @param accessToken
	 * @param groupName 分组名称
	 * @return WechatGroup 
	 */
	public WechatGroup createTagGroup(AbstractAccessToken token, String tagName){
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/create";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", tagName);
		JSONObject obj = new JSONObject();
		obj.put("tag", jsonObj);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String response = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(response);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(json.getString("tag"), WechatGroup.class);
		}
		else {
			System.out.println(IWechatConstants.MSG_TAG+response);
			return null;
		}
	}
	
	/**
	 * 查询所有标签分组
	 * @param token
	 * @return WechatGroup[]
	 */
	public WechatGroup[] getTagGroups(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONArray.parseArray(json.getString("tags"), WechatGroup.class).toArray(new WechatGroup[]{});
		}
		else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 修改分组名称
	 * @param accessToken
	 * @param groupID 分组ID
	 * @param newName 修改后的名称
	 * @return
	 */
	public boolean renameTagGroup(AbstractAccessToken token, int id, String newName) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/update";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", id);
		jsonObj.put("name", newName);
		JSONObject obj = new JSONObject();
		obj.put("tag", jsonObj);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * 删除分组
	 * @param token
	 * @param groupID 需要删除的分组ID
	 * @return
	 */
	public boolean deleteTagGroup(AbstractAccessToken token, int id){
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/delete";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", id);
		JSONObject obj = new JSONObject();
		obj.put("tag", jsonObj);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	public Map<?,?> getFansWithTag(AbstractAccessToken token, int tagid, String nextOpenID){
		String uri = "https://api.weixin.qq.com/cgi-bin/user/tag/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		postData.put("tagid", tagid);
		postData.put("next_openid", nextOpenID);
		HttpEntity requestEntity = new StringEntity(postData.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			Map data = new HashMap();
			data.put("next_openid", json.getString("next_openid"));
			data.put("openid", json.parseArray(json.getJSONObject("data").getString("openid"), String.class));
        	return data;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 批量移动用户到指定标签分组
	 * @param token
	 * @param openIDs 需要移动用户的openid
	 * @param groupID 分组ID
	 * @return
	 */
	public boolean markUsersWithTag(AbstractAccessToken token, String[] openIDs, int tagid) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid_list", Arrays.asList(openIDs));
		jsonObj.put("tagid", tagid);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData, ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	/**
	 * @param token
	 * @param openIDs
	 * @param tagid
	 * @return
	 */
	public boolean unmarkUsersWithTag(AbstractAccessToken token, String[] openIDs, int tagid){
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid_list", Arrays.asList(openIDs));
		jsonObj.put("tagid", tagid);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData, ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	public int[] getTagGroupsOfUser(AbstractAccessToken token, String openID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/getidlist";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject obj = new JSONObject();
		obj.put("openid", openID);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        int[] tagids = null;
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			JSONArray array = json.getJSONArray("tagid_list");
			tagids = new int[array.size()];
			for(int i=0; i<array.size(); i++){
				tagids[i] = array.getIntValue(i);
			}
			return tagids;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	public boolean addNickName(AbstractAccessToken token, String openID, String nickName) {
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid", openID);
		jsonObj.put("remark", nickName);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData, ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	public SubscribedCountInfo getSubscribedUsers(AbstractAccessToken token, String nextOpenID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/user/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		if(StringUtils.isNotBlank(nextOpenID))
			nextOpenID = "";
		queryStr.add(new BasicNameValuePair("next_openid", nextOpenID));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, SubscribedCountInfo.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}

	public UserBlackList getBlacklist(AbstractAccessToken token, String beginOpenID){
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		postData.put("begin_openid", beginOpenID);
		HttpEntity requestEntity = new StringEntity(postData.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, UserBlackList.class);
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	public boolean moveUsers2Blacklist(AbstractAccessToken token, String[] openIDs){
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist";
		
		String result = this.blacklist(token, openIDs, uri);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	public boolean moveUsersOutBlacklist(AbstractAccessToken token, String[] openIDs){
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist";
		String result = this.blacklist(token, openIDs, uri);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		} else {
			System.out.println(IWechatConstants.MSG_TAG+result);
			return false;
		}
	}
	
	private String blacklist(AbstractAccessToken token, String[] openIDs, String uri){
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", token.getAccess_token()));
		JSONObject postData = new JSONObject();
		JSONArray array = new JSONArray();
		postData.put("openid_list", array.addAll(Arrays.asList(openIDs)));
		HttpEntity requestEntity = new StringEntity(postData.toJSONString(), 
				ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		return HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
	}
}
