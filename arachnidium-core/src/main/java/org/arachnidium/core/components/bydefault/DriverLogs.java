package org.arachnidium.core.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.Logs;

public abstract class DriverLogs extends WebdriverInterfaceImplementor
		implements Logs {

	public DriverLogs(WebDriver driver) {
		super(driver);
		delegate = driver.manage().logs();
	}

}
