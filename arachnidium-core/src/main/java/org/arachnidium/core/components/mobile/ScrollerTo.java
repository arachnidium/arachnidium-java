package org.arachnidium.core.components.mobile;

import io.appium.java_client.ScrollsTo;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class ScrollerTo extends WebdriverComponent implements ScrollsTo{

	public ScrollerTo(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}

}
