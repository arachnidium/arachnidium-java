package com.github.arachnidium.selenide.google;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver; //(!!!)
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.model.browser.BrowserApplication;
import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.core.BrowserWindow;

public class Google extends BrowserApplication {
	
	private final static String url = "http://www.google.com/";
	
	protected Google(BrowserWindow browserWindow){
		super(browserWindow);
		//!!!!
		setWebDriver(getWrappedDriver());
	}
	
	public static Google getNew()
	{
		return WebFactory.getApplication(Google.class, url);
	}
	
	public static Google getNew(Configuration config)
	{
		return WebFactory.getApplication(Google.class, config, url);
	}
}
