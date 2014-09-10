package org.arachnidium.core.components.common;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * {@link Alert} implementor
 *
 */
public abstract class AlertHandler extends WebdriverComponent implements Alert {

	/**
	 * Creates an alert representation 
	 * 
	 * @param driver {@link WebDriver} instance 
	 * @param secTimeOut time out in second of awaiting for an alert
	 * @throws NoAlertPresentException
	 */
	public AlertHandler(WebDriver driver, long secTimeOut)
			throws NoAlertPresentException {
		super(driver);
		try {
			delegate = new Awaiting(driver)
					.awaitCondition(secTimeOut,
							ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {
			throw new NoAlertPresentException(e.getMessage(), e);
		}
	}

}
