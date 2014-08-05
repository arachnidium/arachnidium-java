package org.arachnidium.core.components.common;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;

public abstract class NavigationTool extends WebdriverComponent
		implements Navigation {

	public NavigationTool(WebDriver driver) {
		super(driver);
		delegate = driver.navigate();
	}

}
