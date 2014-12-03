package com.github.arachnidium.model.browser;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.core.HowToGetPage;
import com.github.arachnidium.core.WindowManager;
import com.github.arachnidium.model.common.Application;

/**
 * Representation of a browser application
 * 
 * @see Application
 */
public abstract class BrowserApplication extends Application<BrowserWindow, HowToGetPage> {

	protected BrowserApplication(BrowserWindow window) {
		super(window);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WindowManager getManager() {
		return (WindowManager) super.getManager();
	}
}
