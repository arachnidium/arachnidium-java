package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class Shaker extends WebdriverComponent {
	public Shaker(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Simulate shaking the device This is an iOS-only method
	 */
	public abstract void shake();	
}
