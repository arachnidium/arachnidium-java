package org.arachnidium.core.components.bydefault;

import io.appium.java_client.FindsByIosUIAutomation;

import org.openqa.selenium.WebDriver;

public abstract class ByIosUIAutomation extends WebdriverInterfaceImplementor
implements FindsByIosUIAutomation {
	public ByIosUIAutomation(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
