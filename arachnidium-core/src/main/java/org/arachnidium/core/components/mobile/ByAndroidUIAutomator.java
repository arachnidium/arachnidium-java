package org.arachnidium.core.components.mobile;

import io.appium.java_client.FindsByAndroidUIAutomator;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class ByAndroidUIAutomator extends
	WebdriverComponent implements FindsByAndroidUIAutomator {

	public ByAndroidUIAutomator(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
