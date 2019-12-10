package com.as1124.api.wechat.model.shakearound;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * 设备描述模型
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@JSONType
public class DeviceIdentify {

	@JSONField
	long device_id;
	
	@JSONField
	String uuid;
	
	@JSONField
	int major;
	
	@JSONField
	int minor;
	
	public DeviceIdentify() {
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}
	
}
