package com.github.arachnidium.core.components.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;

import com.github.arachnidium.core.components.WebdriverComponent;

/**
 * {@link Window} implementor
 */
public abstract class WindowTool extends WebdriverComponent
		implements Window {

	public WindowTool(WebDriver driver) {
		super(driver);
		delegate = driver.manage().window();
	}

}
