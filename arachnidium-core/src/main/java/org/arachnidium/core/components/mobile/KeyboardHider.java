package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class KeyboardHider extends WebdriverComponent{
	public KeyboardHider(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Hides the keyboard if it is showing. This is an iOS only command.
	 */
	public abstract void hideKeyboard();

	/**
	 * Hides the keyboard by pressing the button specified by keyName if it is
	 * showing. This is an iOS only command.
	 */
	public abstract void hideKeyboard(String keyName);	
}
