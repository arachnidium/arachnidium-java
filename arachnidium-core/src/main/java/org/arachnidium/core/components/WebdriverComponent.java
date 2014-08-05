package org.arachnidium.core.components;

import org.openqa.selenium.WebDriver;

/**
 * @author s.tihomirov For any class that executes specific actions using
 *         webdriver
 */
public abstract class WebdriverComponent {

	protected WebDriver driver;
	protected Object delegate;
	
	public WebdriverComponent(WebDriver driver) {
		this.driver = driver;
	}
}
