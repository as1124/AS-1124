package com.mobile.thirdparty.wechat.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.wechat.IWechatModel;

/**
 * 微信语义模型.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@Deprecated
public class WechatGrammer implements IWechatModel{

	public class GrammerSemantic{
		
		String details;
		
		public GrammerSemantic() {
		}

		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}
	}
	
	/**
	 * 表示请求后的状态
	 */
	int errcode;
	
	/**
	 * 用户输入的字符串
	 */
	String query;
	
	/**
	 * 
	 */
	String category;
	
	/**
	 * 服务的全局类型id，详见协议文档中垂直服务协议定义
	 */
	String type;
	
	/**
	 * 语义理解后的结构化标识，各服务不同 
	 */
	String semantic;
	
	String intent;
	
	/**
	 * 部分类别的结果html5展示，目前不支持 
	 */
	String answer;
	
	/**
	 * 特殊回复说明
	 */
	String text;
	
	/**
	 * 部分类别的结果
	 */
	JSONArray result;
	
	public WechatGrammer() {
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public GrammerSemantic getSemantic() {
		return JSONObject.parseObject(this.semantic, GrammerSemantic.class);
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public JSONArray getResult() {
		return result;
	}

	public void setResult(JSONArray result) {
		this.result = result;
	}
	
}
