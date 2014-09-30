package com.github.arachnidium.core.components.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.github.arachnidium.core.components.WebdriverComponent;

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
