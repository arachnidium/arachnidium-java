package com.github.arachnidium.core.bean;

import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

import com.github.arachnidium.core.eventlisteners.IHandletListener;
import com.github.arachnidium.core.interfaces.IHasHandle;

/**
 * 
 * An abstract listener which will be used by {@link IHasHandle} implementors
 *
 */
abstract class DefaultHandleListener extends AbstractAspect implements IHandletListener {

	public DefaultHandleListener(IConfigurationWrapper configurationWrapper) {
		super(configurationWrapper);
	}

	
}
