package com.mobile.thirdparty.wechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mobile.common.util.LoggerUtil;
import com.mobile.thirdparty.access.AbstractAccessToken;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.wechat.model.user.SubscribedCountInfo;
import com.mobile.thirdparty.wechat.model.user.UserBlackList;
import com.mobile.thirdparty.wechat.model.user.WechatGroup;
import com.mobile.thirdparty.wechat.model.user.WechatUserInfo;

/**
 * WeChat 用户相关操作API.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */

public class UserOperations {

	static Logger logger = LoggerUtil.getLogger(UserOperations.class);

	private UserOperations() {
	}

	/**
	 * 创建用户分组标签
	 * @param accessToken
	 * @param groupName 分组名称
	 * @return WechatGroup 
	 */
	public static WechatGroup createTagGroup(AbstractAccessToken token, String tagName) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/create";
		List<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", tagName);
		JSONObject obj = new JSONObject();
		obj.put("tag", jsonObj);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String response = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(response);
		int returnCode = json.getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(json.getString("tag"), WechatGroup.class);
		} else {
			logger.log(Level.SEVERE, response);
			return null;
		}
	}

	/**
	 * 查询所有标签分组
	 * @param token
	 * @return WechatGroup[]
	 */
	public static List<WechatGroup> getTagGroups(AbstractAccessToken token) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONArray.parseArray(json.getString("tags"), WechatGroup.class);
		} else {
			logger.log(Level.SEVERE, result);
			return Collections.emptyList();
		}
	}

	/**
	 * 修改分组名称
	 * @param token
	 * @param id 分组ID
	 * @param newName 修改后的名称
	 * @return
	 */
	public static boolean renameTagGroup(AbstractAccessToken token, int id, String newName) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/update";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", id);
		jsonObj.put("name", newName);
		JSONObject obj = new JSONObject();
		obj.put("tag", jsonObj);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 删除分组
	 * @param token
	 * @param id 需要删除的分组ID
	 * @return
	 */
	public static boolean deleteTagGroup(AbstractAccessToken token, int id) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/delete";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", id);
		JSONObject obj = new JSONObject();
		obj.put("tag", jsonObj);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 获取标签分组下用户列表, 建议循环调用该方法知道获取列表为空为止
	 * 
	 * @param token
	 * @param tagid
	 * @param nextOpenID 上一次拉取的OPENID列表的最后一个，不填默认从头开始拉取
	 * @return 本次获取的用户openid列表
	 */
	public static List<String> getFansWithTag(AbstractAccessToken token, int tagid, String nextOpenID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/user/tag/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("tagid", tagid);
		postData.put("next_openid", nextOpenID);
		HttpEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseArray(json.getJSONObject("data").getString("openid"), String.class);
		} else {
			logger.log(Level.SEVERE, result);
			return Collections.emptyList();
		}
	}

	/**
	 * 批量移动用户到指定标签分组
	 * @param token
	 * @param openIDs 需要移动用户的openid列表
	 * @param tagid 分组ID
	 * @return
	 */
	public static boolean markUsersWithTag(AbstractAccessToken token, Set<String> openIDs, int tagid) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid_list", openIDs);
		jsonObj.put("tagid", tagid);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData,
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 批量将指定用户从标签组移除
	 * 
	 * @param token
	 * @param openIDs
	 * @param tagid
	 * @return
	 */
	public static boolean unmarkUsersFromTag(AbstractAccessToken token, Set<String> openIDs, int tagid) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid_list", Arrays.asList(openIDs));
		jsonObj.put("tagid", tagid);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData,
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 获取用户身上的标签列表
	 * 
	 * @param token
	 * @param openID
	 * @return
	 */
	public static int[] getUserOwnedTags(AbstractAccessToken token, String openID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/getidlist";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject obj = new JSONObject();
		obj.put("openid", openID);
		StringEntity requestEntity = new StringEntity(obj.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			JSONArray array = json.getJSONArray("tagid_list");
			int[] tagids = new int[array.size()];
			for (int i = 0; i < array.size(); i++) {
				tagids[i] = array.getIntValue(i);
			}
			return tagids;
		} else {
			logger.log(Level.SEVERE, result);
			return new int[0];
		}
	}

	/**
	 * 为指定用户设置备注名称
	 * 
	 * @param token
	 * @param openID
	 * @param nickName
	 * @return
	 */
	public static boolean addNickName(AbstractAccessToken token, String openID, String nickName) {
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("openid", openID);
		jsonObj.put("remark", nickName);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData,
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 获取用户基本信息
	 * @param token
	 * @param openID 用户对该公众号的唯一标识
	 * @return WechatUserInfo
	 */
	public static WechatUserInfo getUserInfo(AbstractAccessToken token, String openID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		queryStr.add(new BasicNameValuePair("openid", openID));
		queryStr.add(new BasicNameValuePair("lang", Locale.getDefault().toString()));
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(response);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(response, WechatUserInfo.class);
		} else {
			logger.log(Level.SEVERE, response);
			return null;
		}
	}

	/**
	 * 批量获取用户信息，默认返回本机系统所代表的地区语言
	 * 
	 * @param token 接口调用凭证
	 * @param openIDs
	 * @return
	 */
	public static List<WechatUserInfo> getUsersInfo(AbstractAccessToken token, Set<String> openIDs) {
		String uri = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postdata = new JSONObject();
		JSONArray userList = new JSONArray();
		Iterator<String> it = openIDs.iterator();
		while (it.hasNext()) {
			JSONObject json = new JSONObject();
			String openid = it.next();
			json.put("openid", openid);
			json.put("lang", Locale.getDefault().toString());
			userList.add(json);
		}
		postdata.put("user_list", userList);
		HttpEntity requestEntity = new StringEntity(postdata.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject returnResult = JSONObject.parseObject(result);
		if (returnResult.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseArray(returnResult.getString("user_info_list"), WechatUserInfo.class);
		} else {
			logger.log(Level.SEVERE, result);
			return Collections.emptyList();
		}
	}

	/**
	 * 获取公众帐号的关注者列表，一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
	 * 
	 * @param token
	 * @param nextOpenID 上一次拉取的OPENID列表的最后一个，不填默认从头开始拉取
	 * @return
	 */
	public static SubscribedCountInfo getSubscribedUsers(AbstractAccessToken token, String nextOpenID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/user/get";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		queryStr.add(new BasicNameValuePair("next_openid", StringUtils.isBlank(nextOpenID) ? "" : nextOpenID));
		String result = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, SubscribedCountInfo.class);
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

	/**
	 * 公获取帐号的黑名单列表（OpenID列表），该接口每次调用最多可拉取 10000 个OpenID，当列表数较多时，可以通过多次拉取的方式来满足需求。
	 * @param token
	 * @param beginOpenID 为空时，默认从开头拉取
	 * @return
	 */
	public static UserBlackList getBlacklist(AbstractAccessToken token, String beginOpenID) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist";
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("begin_openid", beginOpenID);
		HttpEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, UserBlackList.class);
		} else {
			logger.log(Level.SEVERE, result);
			return null;
		}
	}

	/**
	 * 将用户拉入黑名单
	 * @param token
	 * @param openIDs
	 * @return
	 */
	public static boolean moveUsers2Blacklist(AbstractAccessToken token, Set<String> openIDs) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist";

		String result = blacklist(token, openIDs, uri);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	/**
	 * 将用户移出黑名单
	 * @param token
	 * @param openIDs
	 * @return
	 */
	public static boolean moveUsersOutBlacklist(AbstractAccessToken token, Set<String> openIDs) {
		String uri = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist";
		String result = blacklist(token, openIDs, uri);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getIntValue(WechatConstants.ERROR_CODE) == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else {
			logger.log(Level.SEVERE, result);
			return false;
		}
	}

	private static String blacklist(AbstractAccessToken token, Set<String> openIDs, String uri) {
		ArrayList<NameValuePair> queryStr = new ArrayList<>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		JSONArray array = new JSONArray();
		array.addAll(Arrays.asList(openIDs));
		postData.put("openid_list", array);
		HttpEntity requestEntity = new StringEntity(postData.toJSONString(),
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		return HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
	}
}
