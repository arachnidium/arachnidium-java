package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class Swipe extends WebdriverComponent {
	public Swipe(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Convenience method for swiping across the screen
	 */
	public abstract void swipe(int startx, int starty, int endx, int endy, int duration);	
}
