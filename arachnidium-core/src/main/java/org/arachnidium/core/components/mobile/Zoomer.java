package org.arachnidium.core.components.mobile;

import io.appium.java_client.AppiumDriver;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Zoomer extends WebdriverComponent {
	public Zoomer(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Is taken from {@link AppiumDriver}:
	 * Convenience method for "zooming in" on an element on the screen.
	 * "zooming in" refers to the action of two appendages pressing the screen
	 * and sliding away from each other.
	 */
	public abstract void zoom(int x, int y);

	/**
	 * Is taken from {@link AppiumDriver}:
	 * Convenience method for "zooming in" on an element on the screen.
	 * "zooming in" refers to the action of two appendages pressing the screen
	 * and sliding away from each other
	 */
	public abstract void zoom(WebElement el);	
}
