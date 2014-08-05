package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class KeyEventSender extends WebdriverComponent {
	public KeyEventSender(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Send a key event to the device
	 */
	public abstract void sendKeyEvent(int key);	
}
