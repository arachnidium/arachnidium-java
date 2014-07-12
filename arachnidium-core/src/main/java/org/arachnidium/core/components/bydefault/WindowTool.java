package org.arachnidium.core.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;

public abstract class WindowTool extends WebdriverInterfaceImplementor
		implements Window {

	public WindowTool(WebDriver driver) {
		super(driver);
		delegate = driver.manage().window();
	}

}
