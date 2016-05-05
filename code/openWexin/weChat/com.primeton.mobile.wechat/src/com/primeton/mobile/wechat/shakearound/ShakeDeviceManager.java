package com.primeton.mobile.wechat.shakearound;

import java.io.IOException;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.alibaba.fastjson.JSONObject;
import com.eos.system.annotation.Bizlet;
import com.primeton.mobile.wechat.HttpExecuter;
import com.primeton.mobile.wechat.IWechatConstants;
import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.shakearound.DeviceApplication;
import com.primeton.mobile.wechat.model.shakearound.DeviceIdentify;

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
	 * @throws WechatExceprion 
	 */
	@Bizlet("applyDeviceID")
	public static DeviceApplication applyDeviceID(String accessToken, int quantity, String reason, String comment, long poiID) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/shakearound/device/applyid";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject postData = new JSONObject();
		postData.put("quantity", quantity);
		postData.put("apply_reason", reason);
		postData.put("comment", comment);
		postData.put("poi_id", poiID);
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseObject(result, DeviceApplication.class);
		}
        else throw new WechatExceprion("[WechatDeviceManager#applyDeviceID]"+result);
	}
	
	/**
	 * 更新设备信息
	 * @param accessToken
	 * @param deviceIdentify
	 * @param reason
	 * @param comment
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion
	 */
	@Bizlet("updateDeviceInfo")
	public static boolean updateDeviceInfo(String accessToken, DeviceIdentify deviceIdentify, String reason, String comment) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/shakearound/device/update";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject postData = new JSONObject();
		postData.put("device_identifier", deviceIdentify);
		postData.put("comment", comment);
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
        else throw new WechatExceprion("[WechatDeviceManager#updateDeviceInfo]"+result);
	}
	
	/**
	 * 配置设备和门店
	 * @param accessToken
	 * @param quantity
	 * @param reason
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	@Bizlet("bindDeviceWithPoi")
	public static boolean bindDeviceWithPoi(String accessToken, DeviceIdentify deviceIdentifier, long poiID) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/shakearound/device/bindloacation";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject postData = new JSONObject();
		postData.put("device_identifier", deviceIdentifier);
		postData.put("poi_id", poiID);
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
        else throw new WechatExceprion("[WechatDeviceManager#bindDeviceWithPoi]"+result);
	}

	//ATTENTION 查询设备列表未完成
	@Bizlet("queryDeviceList")
	public static boolean queryDeviceList(String accessToken, DeviceIdentify deviceIdentifier) throws IOException, WechatExceprion{
		String uri = "https://api.weixin.qq.com/shakearound/device/bindloacation";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token", accessToken);
		JSONObject postData = new JSONObject();
		postData.put("device_identifier", deviceIdentifier);
		StringRequestEntity requestEntity = new StringRequestEntity(postData.toJSONString());
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
		String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return true;
		}
        else throw new WechatExceprion("[WechatDeviceManager#bindDeviceWithPoi]"+result);
	}
}
