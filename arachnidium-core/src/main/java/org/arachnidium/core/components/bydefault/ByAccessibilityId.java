package org.arachnidium.core.components.bydefault;

import io.appium.java_client.FindsByAccessibilityId;
import org.openqa.selenium.WebDriver;

public abstract class ByAccessibilityId extends WebdriverInterfaceImplementor implements
		FindsByAccessibilityId {
	public ByAccessibilityId(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
