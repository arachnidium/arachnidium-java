package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

/**
 * Gets all defined Strings from an Android app for some language
 *
 */
public abstract class AppStringGetter extends WebdriverComponent{

	public AppStringGetter(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}

	/**
	 * Get all defined Strings from an Android app for the default language
	 *
	 * @return a string of all the localized strings defined in the app
	 */
	public abstract String getAppStrings();

	/**
	 * Get all defined Strings from an Android app for the specified language
	 *
	 * @param language
	 *            strings language code
	 * @return a string of all the localized strings defined in the app
	 */
	public abstract String getAppStrings(String language);
}
