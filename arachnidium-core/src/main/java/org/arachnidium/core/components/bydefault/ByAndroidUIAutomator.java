package org.arachnidium.core.components.bydefault;

import io.appium.java_client.FindsByAndroidUIAutomator;

import org.openqa.selenium.WebDriver;

public abstract class ByAndroidUIAutomator extends
		WebdriverInterfaceImplementor implements FindsByAndroidUIAutomator {

	public ByAndroidUIAutomator(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
