package org.arachnidium.core.components.mobile;

import io.appium.java_client.AppiumDriver;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Tap extends WebdriverComponent {
	public Tap(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Is taken from {@link AppiumDriver}:
	 * Convenience method for tapping a position on the screen
	 */
	public abstract void tap(int fingers, int x, int y, int duration);

	/**
	 * Is taken from {@link AppiumDriver}:
	 * Convenience method for tapping the center of an element on the screen
	 */
	public abstract void tap(int fingers, WebElement element, int duration);	
}
