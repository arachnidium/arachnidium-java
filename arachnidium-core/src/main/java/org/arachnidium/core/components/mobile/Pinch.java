package org.arachnidium.core.components.mobile;

import io.appium.java_client.AppiumDriver;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Pinch extends WebdriverComponent {
	public Pinch(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Is taken from {@link AppiumDriver}:
	 * Convenience method for pinching an element on the screen. "pinching"
	 * refers to the action of two appendages pressing the screen and sliding
	 * towards each other.
	 */
	public abstract void pinch(int x, int y);

	/**
	 * Is taken from {@link AppiumDriver}:
	 * Convenience method for pinching an element on the screen. "pinching"
	 * refers to the action of two appendages pressing the screen and sliding
	 * towards each other.
	 */
	public  abstract  void pinch(WebElement el);	
}
