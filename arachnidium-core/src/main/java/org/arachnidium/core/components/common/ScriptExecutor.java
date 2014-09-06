package org.arachnidium.core.components.common;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * {@link JavascriptExecutor} implementor
 */
public abstract class ScriptExecutor extends WebdriverComponent
		implements JavascriptExecutor {

	public ScriptExecutor(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
