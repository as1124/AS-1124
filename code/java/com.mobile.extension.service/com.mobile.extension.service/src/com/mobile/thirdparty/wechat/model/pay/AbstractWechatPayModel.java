package com.mobile.thirdparty.wechat.model.pay;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.wechat.IWechatModel;

/**
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class AbstractWechatPayModel implements IWechatModel {

	/**
	 * 记录原始数据（xml->json）
	 */
	protected String dataMap;
	
	/**
	 * 请求执行结果
	 */
	protected String return_code;
	
	/**
	 * 返回信息
	 */
	protected String return_msg;
	
	/**
	 * 微信appid
	 */
	protected String appid;
	
	/**
	 * 微信商户号
	 */
	protected String mch_id;
	/**
	 * 签名
	 */
	protected String sign;
	
	/**
	 * 业务执行结果
	 */
	protected String result_code;
	
	/**
	 * 错误码
	 */
	protected String err_code = "";
	
	/**
	 * 错误信息描述
	 */
	protected String err_code_des;
	
	/**
	 * @see #return_code
	 * @return return_code
	 */
	public String getReturn_code() {
		return return_code;
	}

	/**
	 * @param return_code 设置 <code>{@link #return_code}</code>字段的值
	 * @see #return_code
	 */
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	/**
	 * @see #return_msg
	 * @return return_msg
	 */
	public String getReturn_msg() {
		return return_msg;
	}

	/**
	 * @param return_msg 设置 <code>{@link #return_msg}</code>字段的值
	 * @see #return_msg
	 */
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	/**
	 * @see #appid
	 * @return appid
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * @param appid 设置 <code>{@link #appid}</code>字段的值
	 * @see #appid
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @see #mch_id
	 * @return mch_id
	 */
	public String getMch_id() {
		return mch_id;
	}

	/**
	 * @param mch_id 设置 <code>{@link #mch_id}</code>字段的值
	 * @see #mch_id
	 */
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	/**
	 * @see #sign
	 * @return sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign 设置 <code>{@link #sign}</code>字段的值
	 * @see #sign
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @see #result_code
	 * @return result_code
	 */
	public String getResult_code() {
		return result_code;
	}

	/**
	 * @param result_code 设置 <code>{@link #result_code}</code>字段的值
	 * @see #result_code
	 */
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	/**
	 * @see #err_code
	 * @return err_code
	 */
	public String getErr_code() {
		return err_code;
	}

	/**
	 * @param err_code 设置 <code>{@link #err_code}</code>字段的值
	 * @see #err_code
	 */
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	/**
	 * @see #err_code_des
	 * @return err_code_desc
	 */
	public String getErr_code_desc() {
		return err_code_des;
	}

	/**
	 * @param err_code_desc 设置 <code>{@link #err_code_des}</code>字段的值
	 * @see #err_code_desc
	 */
	public void setErr_code_des(String err_code_desc) {
		this.err_code_des = err_code_desc;
	}
	
	/**
	 * 获取字段值
	 * @param key 
	 * @return
	 */
	public String getProperty(String key){
		return JSONObject.parseObject(this.dataMap).getString(key);
	}
	
	/**
	 * 原始字段数据信息，json格式串
	 * @param data
	 */
	public void setDataMap(String data){
		this.dataMap = data;
	}
}
