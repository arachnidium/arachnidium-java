package com.github.arachnidium.web.google;

import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserPage;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.support.annotations.rootelements.RootElement;

/**
 * 
 * Any page opened by Google
 *
 */
public class AnyPage extends BrowserPage {

	@Static
	@RootElement(chain = {@FindBy(id = "content")})
	public FunctionalPart<?> content;
	
	public AnyPage(BrowserWindow browserWindow){
		super(browserWindow);
	}
}
