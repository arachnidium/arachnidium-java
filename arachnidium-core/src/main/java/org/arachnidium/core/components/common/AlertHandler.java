package org.arachnidium.core.components.common;

import org.arachnidium.core.components.ComponentFactory;
import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class AlertHandler extends WebdriverComponent implements Alert {

	public AlertHandler(WebDriver driver, long secTimeOut)
			throws NoAlertPresentException {
		super(driver);
		try {
			delegate = ComponentFactory.getComponent(Awaiting.class, driver)
					.awaitCondition(secTimeOut,
							ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {
			throw new NoAlertPresentException(e.getMessage(), e);
		}
	}

}
