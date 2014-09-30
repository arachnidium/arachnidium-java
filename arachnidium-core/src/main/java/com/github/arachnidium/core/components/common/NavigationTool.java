package com.github.arachnidium.core.components.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;

import com.github.arachnidium.core.components.WebdriverComponent;

/**
 * {@link Navigation} implementor
 */
public abstract class NavigationTool extends WebdriverComponent
		implements Navigation {

	public NavigationTool(WebDriver driver) {
		super(driver);
		delegate = driver.navigate();
	}

}
