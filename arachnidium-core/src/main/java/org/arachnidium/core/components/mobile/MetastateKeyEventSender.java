package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class MetastateKeyEventSender extends
	WebdriverComponent {
	public MetastateKeyEventSender(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Send a key event along with an Android metastate to an Android device
	 * Metastates are things like *shift* to get uppercase characters
	 */
	public abstract void sendKeyEvent(int key, Integer metastate);	
}
