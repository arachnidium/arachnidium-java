package org.arachnidium.core.components.common;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.Logs;

public abstract class DriverLogs extends WebdriverComponent
		implements Logs {

	public DriverLogs(WebDriver driver) {
		super(driver);
		delegate = driver.manage().logs();
	}

}
