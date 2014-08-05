package org.arachnidium.core.components.mobile;

import io.appium.java_client.FindsByAccessibilityId;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class ByAccessibilityId extends WebdriverComponent
		implements FindsByAccessibilityId {
	public ByAccessibilityId(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
