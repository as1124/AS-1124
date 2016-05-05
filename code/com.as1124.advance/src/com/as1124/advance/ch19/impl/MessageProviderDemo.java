package com.as1124.advance.ch19.impl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

import com.as1124.advance.ch19.IMessageProvider;

public class MessageProviderDemo implements IMessageProvider, IExecutableExtension {

	public MessageProviderDemo() {
	}

	@Override
	public String getTitle() {
		return "MessageProvider";
	}

	@Override
	public String getMessage() {
		return "这是扩展提供的欢迎信息";
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		
	}

}
