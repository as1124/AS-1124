package com.as1124.api.wechat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.as1124.api.access.AbstractAccessToken;
import com.as1124.api.access.HttpExecuter;
import com.as1124.api.access.exception.ThirdPartyRequestExceprion;
import com.as1124.api.wechat.model.shakearound.DeviceApplication;
import com.as1124.api.wechat.model.shakearound.DeviceIdentify;

/**
 * 摇一摇周边/设备管理<br>
 * 申请配置所需要的UUID, Major, Minor。申请成功后返回批次ID，可用返回的批次ID用“查询设备列表”接口
 * 拉取本次申请的设备ID。单次新增设备超过500个，需要走人工审核流程呢，大概三个工作日
 * 
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public class ShakeDeviceManager {

	static Logger logger = Logger.getLogger(ShakeDeviceManager.class.getName());

	private ShakeDeviceManager() {
	}

	/**
	 * 申请设备ID
	 * @param token
	 * @param quantity 申请的设备ID的数量，单次新增设备超过500个，需要人工审核流程
	 * @param reason 申请原因，不超过100个字
	 * @param comment 备注，不超过15个汉字或30个英文字母
	 * @param poiID 设备关联的门店ID
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static DeviceApplication applyDeviceID(AbstractAccessToken token, int quantity, String reason,
			String comment, long poiID) throws IOException, ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/shakearound/device/applyid";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("quantity", quantity);
		postData.put("apply_reason", reason);
		postData.put("comment", comment);
		postData.put("poi_id", poiID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return JSONObject.parseObject(result, DeviceApplication.class);
		} else
			throw new ThirdPartyRequestExceprion("[WechatDeviceManager#applyDeviceID]" + result);
	}

	/**
	 * 更新设备信息
	 * @param token
	 * @param deviceIdentify
	 * @param comment
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion
	 */
	public static boolean updateDeviceInfo(AbstractAccessToken token, DeviceIdentify deviceIdentify, String comment)
			throws IOException, ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/shakearound/device/update";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("device_identifier", deviceIdentify);
		postData.put("comment", comment);
		StringEntity requestEntity = new StringEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else
			throw new ThirdPartyRequestExceprion("[WechatDeviceManager#updateDeviceInfo]" + result);
	}

	/**
	 * 配置设备和门店
	 * @param token
	 * @param deviceIdentifier
	 * @param poiID
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static boolean bindDeviceWithPoi(AbstractAccessToken token, DeviceIdentify deviceIdentifier, long poiID)
			throws IOException, ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/shakearound/device/bindloacation";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, token.getAccessToken()));
		JSONObject postData = new JSONObject();
		postData.put("device_identifier", deviceIdentifier);
		postData.put("poi_id", poiID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else
			throw new ThirdPartyRequestExceprion("[WechatDeviceManager#bindDeviceWithPoi]" + result);
	}

	//ATTENTION 查询设备列表未完成
	public static boolean queryDeviceList(String accessToken, DeviceIdentify deviceIdentifier)
			throws IOException, ThirdPartyRequestExceprion {
		String uri = "https://api.weixin.qq.com/shakearound/device/bindloacation";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("device_identifier", deviceIdentifier);
		StringEntity requestEntity = new StringEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		int returnCode = JSONObject.parseObject(result).getIntValue(WechatConstants.ERROR_CODE);
		if (returnCode == WechatConstants.RETURN_CODE_SUCCESS) {
			return true;
		} else
			throw new ThirdPartyRequestExceprion("[WechatDeviceManager#bindDeviceWithPoi]" + result);
	}
}
