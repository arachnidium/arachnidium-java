package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Pinch extends WebdriverComponent {
	public Pinch(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Convenience method for pinching an element on the screen. "pinching"
	 * refers to the action of two appendages pressing the screen and sliding
	 * towards each other.
	 */
	public abstract void pinch(int x, int y);

	/**
	 * Convenience method for pinching an element on the screen. "pinching"
	 * refers to the action of two appendages pressing the screen and sliding
	 * towards each other.
	 */
	public  abstract  void pinch(WebElement el);	
}
