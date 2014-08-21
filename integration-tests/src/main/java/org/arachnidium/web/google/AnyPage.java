package org.arachnidium.web.google;

import org.arachnidium.model.browser.BrowserPage;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.core.BrowserWindow;

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
