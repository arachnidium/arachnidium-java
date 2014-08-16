package org.arachnidium.model.browser;

import org.arachnidium.core.BrowserWindow;
import org.arachnidium.core.HowToGetBrowserWindow;
import org.arachnidium.core.WindowManager;
import org.arachnidium.core.components.common.Cookies;
import org.arachnidium.model.common.Application;

/**
 * Representation of a browser application
 */
public abstract class BrowserApplication extends Application<BrowserWindow, HowToGetBrowserWindow> {

	protected final Cookies cookies;

	protected BrowserApplication(BrowserWindow window) {
		super(window);
		cookies = driverEncapsulation.getComponent(Cookies.class);
	}

	public WindowManager getWindowManager() {
		return (WindowManager) manager;
	}
}
