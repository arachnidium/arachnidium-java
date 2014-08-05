package org.arachnidium.core.components.mobile;

import io.appium.java_client.FindsByIosUIAutomation;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class ByIosUIAutomation extends WebdriverComponent
implements FindsByIosUIAutomation {
	public ByIosUIAutomation(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
