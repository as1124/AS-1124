package com.mobile.wechat.shakearound;

import java.io.IOException;




import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.WechatConstants;
import com.mobile.thirdparty.wechat.model.shakearound.DeviceApplication;
import com.mobile.thirdparty.wechat.model.shakearound.DeviceIdentify;

/**
 * 摇一摇周边/设备管理<br>
 * 申请配置所需要的UUID, Major, Minor。申请成功后返回批次ID，可用返回的批次ID用“查询设备列表”接口
 * 拉取本次申请的设备ID。单次新增设备超过500个，需要走人工审核流程呢，大概三个工作日
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ShakeDeviceManager {
	
	/**
	 * 申请设备ID
	 * @param accessToken
	 * @param quantity 申请的设备ID的数量，单次新增设备超过500个，需要人工审核流程
	 * @param reason 申请原因，不超过100个字
	 * @param comment 备注，不超过15个汉字或30个英文字母
	 * @param poiID 设备关联的门店ID
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static DeviceApplication applyDeviceID(String accessToken, int quantity, String reason, String comment, long poiID) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/shakearound/device/applyid";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("quantity", quantity);
		postData.put("apply_reason", reason);
		postData.put("comment", comment);
		postData.put("poi_id", poiID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(WechatConstants.ERROR_CODE);
        if(returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, DeviceApplication.class);
		}
        else throw new ThirdPartyRequestExceprion("[WechatDeviceManager#applyDeviceID]"+result);
	}
	
	/**
	 * 更新设备信息
	 * @param accessToken
	 * @param deviceIdentify
	 * @param reason
	 * @param comment
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion
	 */
	public static boolean updateDeviceInfo(String accessToken, DeviceIdentify deviceIdentify, String reason, String comment) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/shakearound/device/update";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("device_identifier", deviceIdentify);
		postData.put("comment", comment);
		StringEntity requestEntity = new StringEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(WechatConstants.ERROR_CODE);
        if(returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
        else throw new ThirdPartyRequestExceprion("[WechatDeviceManager#updateDeviceInfo]"+result);
	}
	
	/**
	 * 配置设备和门店
	 * @param accessToken
	 * @param quantity
	 * @param reason
	 * @return
	 * @throws IOException
	 * @throws ThirdPartyRequestExceprion 
	 */
	public static boolean bindDeviceWithPoi(String accessToken, DeviceIdentify deviceIdentifier, long poiID) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/shakearound/device/bindloacation";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("device_identifier", deviceIdentifier);
		postData.put("poi_id", poiID);
		StringEntity requestEntity = new StringEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(WechatConstants.ERROR_CODE);
        if(returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
        else throw new ThirdPartyRequestExceprion("[WechatDeviceManager#bindDeviceWithPoi]"+result);
	}

	//ATTENTION 查询设备列表未完成
	public static boolean queryDeviceList(String accessToken, DeviceIdentify deviceIdentifier) throws IOException, ThirdPartyRequestExceprion{
		String uri = "https://api.weixin.qq.com/shakearound/device/bindloacation";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject postData = new JSONObject();
		postData.put("device_identifier", deviceIdentifier);
		StringEntity requestEntity = new StringEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(WechatConstants.ERROR_CODE);
        if(returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
        else throw new ThirdPartyRequestExceprion("[WechatDeviceManager#bindDeviceWithPoi]"+result);
	}
}
