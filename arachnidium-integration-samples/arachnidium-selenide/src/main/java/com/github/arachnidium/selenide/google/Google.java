package com.github.arachnidium.selenide.google;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver; //(!!!)

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserApplication;

public class Google extends BrowserApplication {
		
	protected Google(BrowserWindow browserWindow){
		super(browserWindow);
		//!!!!
		setWebDriver(getWrappedDriver());
	}
}
