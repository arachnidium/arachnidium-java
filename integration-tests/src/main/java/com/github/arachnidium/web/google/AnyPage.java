package com.github.arachnidium.web.google;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserPage;
import com.github.arachnidium.model.common.FunctionalPart;

/**
 * 
 * Any page opened by Google
 *
 */
public class AnyPage extends BrowserPage {

	FunctionalPart<?> part;
	public AnyPage(BrowserWindow browserWindow){
		super(browserWindow);
		part = getPart(FunctionalPart.class);
	}
}
