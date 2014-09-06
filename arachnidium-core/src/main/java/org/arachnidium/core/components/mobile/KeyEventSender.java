package org.arachnidium.core.components.mobile;

import io.appium.java_client.AppiumDriver;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class KeyEventSender extends WebdriverComponent {
	public KeyEventSender(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Is taken from {@link AppiumDriver}:
	 * Send a key event to the device
	 */
	public abstract void sendKeyEvent(int key);	
	
	/**
	 * Is taken from {@link AppiumDriver}:
	 * Send a key event along with an Android metastate to an Android device
	 * Metastates are things like *shift* to get uppercase characters
	 *
	 * @param key code for the key pressed on the Android device
	 * @param metastate metastate for the keypress
	 */
	 public abstract void  sendKeyEvent(int key, Integer metastate);
}
