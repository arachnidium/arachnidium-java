package org.arachnidium.model.mobile;

import org.arachnidium.core.ContextManager;
import org.arachnidium.core.HowToGetMobileContext;
import org.arachnidium.core.MobileContext;
import org.arachnidium.model.common.Application;

/**
 * Representation of a mobile application
 */
public abstract class MobileAppliction extends Application<MobileContext, HowToGetMobileContext> {

	protected MobileAppliction(MobileContext context) {
		super(context);
	}

	public ContextManager getContextManager() {
		return (ContextManager) manager;
	}

}
