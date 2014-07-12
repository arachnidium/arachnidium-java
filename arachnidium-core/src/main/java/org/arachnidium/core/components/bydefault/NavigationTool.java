package org.arachnidium.core.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;

public abstract class NavigationTool extends WebdriverInterfaceImplementor
		implements Navigation {

	public NavigationTool(WebDriver driver) {
		super(driver);
		delegate = driver.navigate();
	}

}
