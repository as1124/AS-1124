package com.mobile.thirdparty.wechat.model.shakearound;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * 设备申请模型
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@JSONType
public class DeviceApplication {

	@JSONField
	List<DeviceIdentify> device_identifiers;
	
	@JSONField
	int app_id;
	
	/**
	 * 审核状态：0-审核未通过，1-审核中，2-审核通过
	 */
	@JSONField
	int audit_status;
	
	/**
	 * 审核备注信息
	 */
	@JSONField
	String audit_comment;
	
	public DeviceApplication() {
	}

	public List<DeviceIdentify> getDevice_identifiers() {
		return device_identifiers;
	}

	public void setDevice_identifiers(List<DeviceIdentify> device_identifiers) {
		this.device_identifiers = device_identifiers;
	}

	public int getApp_id() {
		return app_id;
	}

	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}

	public int getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(int audit_status) {
		this.audit_status = audit_status;
	}

	public String getAudit_comment() {
		return audit_comment;
	}

	public void setAudit_comment(String audit_comment) {
		this.audit_comment = audit_comment;
	}
	
}
