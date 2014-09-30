package com.github.arachnidium.core.components.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.Logs;

import com.github.arachnidium.core.components.WebdriverComponent;

/**
 * {@link Logs} implementor
 *
 */
public abstract class DriverLogs extends WebdriverComponent
		implements Logs {

	public DriverLogs(WebDriver driver) {
		super(driver);
		delegate = driver.manage().logs();
	}

}
