package org.arachnidium.core.components.mobile;

import io.appium.java_client.AppiumDriver;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class KeyboardHider extends WebdriverComponent{
	public KeyboardHider(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * Is taken from {@link AppiumDriver}:
	 * Hides the keyboard if it is showing. This is an iOS only command.
	 */
	public abstract void hideKeyboard();

	/**
	 * Is taken from {@link AppiumDriver}:
	 * Hides the keyboard by pressing the button specified by keyName if it is
	 * showing. This is an iOS only command.
	 */
	public abstract void hideKeyboard(String keyName);	
	
	/**
	 * Is taken from {@link AppiumDriver}:
	 * Hides the keyboard if it is showing. Available strategies are PRESS_KEY and TAP_OUTSIDE.
	 * One taps outside the keyboard, the other presses a key of your choosing (probably the 'Done' key).
	 * Hiding the keyboard often depends on the way an app is implemented, no single strategy always works.
	 *
	 * These parameters are only for iOS, and ignored by Android.
	 *
	 * @param strategy HideKeyboardStrategy
	 * @param keyName a String, representing the text displayed on the button of the keyboard you want to press. For example: "Done"
	 */
	public abstract void hideKeyboard(String strategy, String keyName);	
}
