package org.arachnidium.core.bean;

import org.arachnidium.core.eventlisteners.IHandletListener;
import org.arachnidium.core.interfaces.IHasHandle;
import org.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

/**
 * 
 * An abstract listener to {@link IHasHandle} implementations 
 *
 */
abstract class DefaultHandleListener extends AbstractAspect implements IHandletListener {

	public DefaultHandleListener(IConfigurationWrapper configurationWrapper) {
		super(configurationWrapper);
	}

	
}
