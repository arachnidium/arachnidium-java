package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.components.overriden.Awaiting;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class AlertHandler extends WebdriverInterfaceImplementor
		implements Alert {

	public AlertHandler(WebDriver driver, long secTimeOut)
			throws NoAlertPresentException {
		super(driver);
		try {
			delegate = new Awaiting(driver).awaitCondition(secTimeOut,
					ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {
			throw new NoAlertPresentException(e.getMessage(), e);
		}
	}

}
