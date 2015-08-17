package com.github.arachnidium.core.bean;

import com.github.arachnidium.core.interfaces.IHasHandle;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

abstract class AbstractBeanHandleConfiguration {
	
	protected IConfigurationWrapper configurationWrapper;
	
	public <T extends IHasHandle> T getHandle(T handle, IConfigurationWrapper configurationWrapper){
		this.configurationWrapper = configurationWrapper;
		return handle;
	}
	
	abstract <T extends AbstractAspect> T getHandleAspect();
}
