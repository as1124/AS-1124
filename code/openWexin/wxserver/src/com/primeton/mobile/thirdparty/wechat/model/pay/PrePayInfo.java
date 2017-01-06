package com.primeton.mobile.thirdparty.wechat.model.pay;

import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.thirdparty.wechat.IWechatModel;

/**
 * 微信支付，预订单信息模型
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class PrePayInfo implements IWechatModel{
	
	private String dataMap = "";
	
	String return_code;
	
	String return_msg;
	
	String appid;
	
	String mch_id;
	
	String device_info;
	
	String nonce_str;
	
	String sign;
	
	String result_code;
	
	String err_code;
	
	String err_code_desc;
	
	String trade_type;
	
	String prepay_id;
	
	String code_url;
	
	public PrePayInfo() {
		
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_desc() {
		return err_code_desc;
	}

	public void setErr_code_desc(String err_code_desc) {
		this.err_code_desc = err_code_desc;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}
	
	public void setDataMap(String dataMap){
		this.dataMap = dataMap;
	}
	
	public String getProperty(String key){
		return JSONObject.parseObject(this.dataMap).getString(key);
	}
	
}
