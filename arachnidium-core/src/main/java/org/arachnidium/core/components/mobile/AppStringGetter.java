package org.arachnidium.core.components.mobile;

import io.appium.java_client.AppiumDriver;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

/**
 * Is taken from {@link AppiumDriver}
 * Gets all defined Strings from an Android app for some language
 *
 */
public abstract class AppStringGetter extends WebdriverComponent{

	public AppStringGetter(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}

	/**
	 * Is taken from {@link AppiumDriver}
	 * Get all defined Strings from an Android app for the default language
	 *
	 * @return a string of all the localized strings defined in the app
	 */
	public abstract String getAppStrings();

	/**
	 * Is taken from {@link AppiumDriver}
	 * Get all defined Strings from an Android app for the specified language
	 *
	 * @param language
	 *            strings language code
	 * @return a string of all the localized strings defined in the app
	 */
	public abstract String getAppStrings(String language);
}
