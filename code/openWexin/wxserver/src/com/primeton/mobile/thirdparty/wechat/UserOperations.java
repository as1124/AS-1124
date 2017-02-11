package com.primeton.mobile.thirdparty.wechat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.primeton.mobile.thirdparty.wechat.model.user.SubscribedCountInfo;
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
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatUserInfo getUserInfo(String accessToken, String openID) throws JSONException, IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		queryStr.add(new BasicNameValuePair("openid", openID));
		queryStr.add(new BasicNameValuePair("lang", Locale.getDefault().toString()));
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(response);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(response, WechatUserInfo.class);
		}
		else throw new ThirdPartyRequestExceprion("[UserOperations#WechatUserInfo]"+response);
	}
	
	
//	
//	public static void main(String[] args){
//		try {
//			String at = "GPBYjgveJ1UjAaZHhNgqsCyG5Tvz41HRovh9-aAO35iIDEKNyk6WKIfQUho7RHeTpiJmeb54XBnGAH5vow44wuX3-q7KkdwqaVooKW0_1SLnMENPOMQ9e1UVuHYsKgXAPFJjAIAVLP";
//			WechatAccessToken token = WechatAccessToken.getToken("wxfad56e1d17a29e04", "d048ee6acfb69cbd0fc3aaa591b1455b");
//			WechatUserInfo info = getUserInfo(token.getAccess_token(), "ooyKpxIA8sqiWbSE89yJJl34uSsE");
//			System.out.println(info);
//			String text = "Ivyߤߘߘߘ谨防假冒";
//			int length = text.length();
//			StringBuffer temp = new StringBuffer();
//			for(int i=0; i<length; i++){
//				char a = text.charAt(i);
//				int codePoint = text.codePointAt(i);
//				if(codePoint>33&&codePoint < 127){
//					//英文字符
//					temp.append(a);
//				} else if(codePoint>=19968 && codePoint<=40869){
//					//匹配中文字符的正则表达式： [\u4e00-\u9fa5]
//					temp.append(a);
//				}
//			}
//			System.out.println(temp.toString());
//		} catch (WechatExceprion e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		
//	}
	
	/**
	 * 创建用户分组
	 * @param groupName 分组名称
	 * @param accessToken
	 * @return WechatGroup
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatGroup createNewGroup(String groupName, String accessToken) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/create";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", groupName);
		JSONObject obj = new JSONObject();
		obj.put("group", jsonObj);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String response = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(response);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(json.getString("group"), WechatGroup.class);
		}
		else throw new ThirdPartyRequestExceprion("[UserOperations#createNewGroup]"+response);
	}
	
	/**
	 * 查询所有分组
	 * @param accessToken
	 * @return WechatGroup[]
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static WechatGroup[] getAllGroups(String accessToken) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONArray.parseArray(json.getString("groups"), WechatGroup.class).toArray(new WechatGroup[]{});
		}
		else throw new ThirdPartyRequestExceprion("[UserOperations#getAllGroups]"+result);
	}
	
	/**
	 * 查询用户所在分组
	 * @param accessToken
	 * @param openID 用户对该公众号的唯一标识
	 * @return groupid
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static String searchUserGroup(String accessToken, String openID) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/getid";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject obj = new JSONObject();
		obj.put("openid", openID);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return json.getString("groupid");
		}
		else throw new ThirdPartyRequestExceprion("[UserOperations#searchUserGroups]"+result);
	}
	
	/**
	 * 修改分组名称
	 * @param accessToken
	 * @param groupID 分组ID
	 * @param newName 修改后的名称
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static boolean renameGroup(String accessToken, int groupID, String newName) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/update";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", groupID);
		jsonObj.put("name", newName);
		JSONObject obj = new JSONObject();
		obj.put("group", jsonObj);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new ThirdPartyRequestExceprion("[UserOperations#renameGroup]"+result);
	}
	
	/**
	 * 移动用户到指定分组
	 * @param accessToken
	 * @param openID 用户对当前公众号的唯一标识
	 * @param groupID 分组ID
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static boolean moveUser2Group(String accessToken, String openID, int groupID) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/members/update";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid", openID);
		jsonObj.put("to_groupid", groupID);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData, ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new ThirdPartyRequestExceprion("[UserOperations#moveUser2Group]"+result);
	}
	
	/**
	 * 批量移动用户到指定分组
	 * @param accessToken
	 * @param openIDs 需要移动用户的openid
	 * @param groupID 分组ID
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static boolean moveUsers2Group(String accessToken, String[] openIDs, int groupID) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid_list", Arrays.asList(openIDs));
		jsonObj.put("to_groupid", groupID);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData, ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new ThirdPartyRequestExceprion("[UserOperations#moveUsers2Group]"+result);
	}
	
	/**
	 * 删除分组
	 * @param accessToken
	 * @param groupID 需要删除的分组ID
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static boolean dropGroup(String accessToken, int groupID) throws IOException, ThirdPartyRequestExceprion{
		//ATTENTION 总是 errcode = -1
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/delete";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", groupID);
		JSONObject obj = new JSONObject();
		obj.put("group", jsonObj);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(), ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new ThirdPartyRequestExceprion("[UserOperations#dropGroup]"+result);
	}
	
	/**
	 * 设置用户备注名
	 * @param accessToken
	 * @param openID 用户对当前公众号的唯一ID
	 * @param remark 备注信息
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static boolean remarkUser(String accessToken, String openID, String remark) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid", openID);
		jsonObj.put("remark", remark);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData, ContentType.create(
				IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new ThirdPartyRequestExceprion("[UserOperations#remarkUser]"+result);
	}
	
	/**
	 * 获取关注的用户列表，<strong>一次拉取调用最多拉取10000个关注者的OpenID</strong>
	 * @param accessToken
	 * @param nextOpenID 下一个用户的openid,
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static SubscribedCountInfo getSubscribedUsers(String accessToken, String nextOpenID) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/user/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		if(StringUtils.isNotBlank(nextOpenID))
			nextOpenID = "";
		queryStr.add(new BasicNameValuePair("next_openid", nextOpenID));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, SubscribedCountInfo.class);
		}
		else throw new ThirdPartyRequestExceprion("[UserOperations#getSubscribedUsers]"+result);
	}

}
