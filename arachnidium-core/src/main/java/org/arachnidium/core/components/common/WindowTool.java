package org.arachnidium.core.components.common;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;

public abstract class WindowTool extends WebdriverComponent
		implements Window {

	public WindowTool(WebDriver driver) {
		super(driver);
		delegate = driver.manage().window();
	}

}
