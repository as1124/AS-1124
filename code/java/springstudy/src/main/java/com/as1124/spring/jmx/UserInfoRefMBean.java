package com.as1124.spring.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.as1124.spring.web.model.UserInfo;

/**
 * {@link ManagedOperation}只暴露操作并不暴露属性本身；
 * <br/>
 * {@link ManagedAttribute}暴露操作的同时也暴露属性（只读，可读写）
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@ManagedResource(objectName = "MbeanXml:name=userRef")
public class UserInfoRefMBean extends UserInfo {

	private static final long serialVersionUID = 1L;

	private String telphone;

	private boolean isMarryed;

	@ManagedOperation
	public String getTelphone() {
		return telphone;
	}

	@ManagedOperation
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	@ManagedAttribute
	public boolean isMarryed() {
		return isMarryed;
	}

	public void setMarryed(boolean isMarryed) {
		this.isMarryed = isMarryed;
	}

}
