package org.arachnidium.model.browser;

import org.arachnidium.core.BrowserWindow;
import org.arachnidium.core.HowToGetBrowserWindow;
import org.arachnidium.core.WindowManager;
import org.arachnidium.model.common.Application;

/**
 * Representation of a browser application
 * 
 * @see Application
 */
public abstract class BrowserApplication extends Application<BrowserWindow, HowToGetBrowserWindow> {

	protected BrowserApplication(BrowserWindow window) {
		super(window);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WindowManager getManager() {
		return (WindowManager) super.getManager();
	}
}
