package com.primeton.mobile.wechat;

import java.io.IOException;
import java.util.Locale;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.eos.system.annotation.Bizlet;
import com.eos.system.utility.StringUtil;
import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.user.SubscribedCountInfo;
import com.primeton.mobile.wechat.model.user.WechatGroup;
import com.primeton.mobile.wechat.model.user.WechatUserInfo;

import edu.emory.mathcs.backport.java.util.Arrays;

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
	 * @throws WechatExceprion 
	 */
	@Bizlet("getUserInfo")
	public static WechatUserInfo getUserInfo(String accessToken, String openID) throws JSONException, HttpException, IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info";
		NameValuePair[] queryStr = new NameValuePair[3];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		queryStr[1] = new NameValuePair("openid", openID);
		queryStr[2] = new NameValuePair("lang", Locale.getDefault().toString());
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(response);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(response, WechatUserInfo.class);
		}
		else throw new WechatExceprion("[UserOperations#WechatUserInfo]"+response);
	}
	
	/**
	 * 创建用户分组
	 * @param groupName 分组名称
	 * @param accessToken
	 * @return WechatGroup
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("createNewGroup")
	public static WechatGroup createNewGroup(String groupName, String accessToken) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/create";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", groupName);
		JSONObject obj = new JSONObject();
		obj.put("group", jsonObj);
		RequestEntity requestEntity = new StringRequestEntity(obj.toJSONString(), IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String response = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(response);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(json.getString("group"), WechatGroup.class);
		}
		else throw new WechatExceprion("[UserOperations#createNewGroup]"+response);
	}
	
	/**
	 * 查询所有分组
	 * @param accessToken
	 * @return WechatGroup[]
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("getAllGroups")
	public static WechatGroup[] getAllGroups(String accessToken) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/get";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token",accessToken);
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONArray.parseArray(json.getString("groups"), WechatGroup.class).toArray(new WechatGroup[]{});
		}
		else throw new WechatExceprion("[UserOperations#getAllGroups]"+result);
	}
	
	/**
	 * 查询用户所在分组
	 * @param accessToken
	 * @param openID 用户对该公众号的唯一标识
	 * @return groupid
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("searchUserGroups")
	public static String searchUserGroup(String accessToken, String openID) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/getid";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject obj = new JSONObject();
		obj.put("openid", openID);
		RequestEntity requestEntity = new StringRequestEntity(obj.toJSONString(), IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return json.getString("groupid");
		}
		else throw new WechatExceprion("[UserOperations#searchUserGroups]"+result);
	}
	
	/**
	 * 修改分组名称
	 * @param accessToken
	 * @param groupID 分组ID
	 * @param newName 修改后的名称
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("renameGroup")
	public static boolean renameGroup(String accessToken, int groupID, String newName) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/update";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token",accessToken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", groupID);
		jsonObj.put("name", newName);
		JSONObject obj = new JSONObject();
		obj.put("group", jsonObj);
		RequestEntity requestEntity = new StringRequestEntity(obj.toJSONString(), IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new WechatExceprion("[UserOperations#renameGroup]"+result);
	}
	
	/**
	 * 移动用户到指定分组
	 * @param accessToken
	 * @param openID 用户对当前公众号的唯一标识
	 * @param groupID 分组ID
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("moveUser2Group")
	public static boolean moveUser2Group(String accessToken, String openID, int groupID) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/members/update";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token",accessToken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid", openID);
		jsonObj.put("to_groupid", groupID);
		String postData = jsonObj.toString();
		RequestEntity requestEntity = new StringRequestEntity(postData, IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new WechatExceprion("[UserOperations#moveUser2Group]"+result);
	}
	
	/**
	 * 批量移动用户到指定分组
	 * @param accessToken
	 * @param openIDs 需要移动用户的openid
	 * @param groupID 分组ID
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("moveUsers2Group")
	public static boolean moveUsers2Group(String accessToken, String[] openIDs, int groupID) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token",accessToken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid_list", Arrays.asList(openIDs));
		jsonObj.put("to_groupid", groupID);
		String postData = jsonObj.toString();
		RequestEntity requestEntity = new StringRequestEntity(postData, IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new WechatExceprion("[UserOperations#moveUsers2Group]"+result);
	}
	
	/**
	 * 删除分组
	 * @param accessToken
	 * @param groupID 需要删除的分组ID
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("dropGroup")
	public static boolean dropGroup(String accessToken, int groupID) throws IOException, WechatExceprion{
		//ATTENTION 总是 errcode = -1
		String uri = "https://api.weixin.qq.com/cgi-bin/groups/delete";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", groupID);
		JSONObject obj = new JSONObject();
		obj.put("group", jsonObj);
		RequestEntity requestEntity = new StringRequestEntity(obj.toJSONString(), IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new WechatExceprion("[UserOperations#dropGroup]"+result);
	}
	
	/**
	 * 设置用户备注名
	 * @param accessToken
	 * @param openID 用户对当前公众号的唯一ID
	 * @param remark 备注信息
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("remarkUser")
	public static boolean remarkUser(String accessToken, String openID, String remark) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token",accessToken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid", openID);
		jsonObj.put("remark", remark);
		String postData = jsonObj.toString();
		RequestEntity requestEntity = new StringRequestEntity(postData, IWechatConstants.CONTENT_TYPE, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
		else throw new WechatExceprion("[UserOperations#remarkUser]"+result);
	}
	
	/**
	 * 获取关注的用户列表，<strong>一次拉取调用最多拉取10000个关注者的OpenID</strong>
	 * @param accessToken
	 * @param nextOpenID 下一个用户的openid,
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("getSubscribedUsers")
	public static SubscribedCountInfo getSubscribedUsers(String accessToken, String nextOpenID) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/user/get";
		NameValuePair[] queryStr = new NameValuePair[2];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		if(StringUtil.isNullOrBlank(nextOpenID))
			nextOpenID = "";
		queryStr[1] = new NameValuePair("next_openid", nextOpenID);
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, SubscribedCountInfo.class);
		}
		else throw new WechatExceprion("[UserOperations#getSubscribedUsers]"+result);
	}

}
