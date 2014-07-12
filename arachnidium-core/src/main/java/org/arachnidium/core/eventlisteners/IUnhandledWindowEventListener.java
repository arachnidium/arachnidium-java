package org.arachnidium.core.eventlisteners;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public interface IUnhandledWindowEventListener {
	public void whenNoAlertThere(WebDriver weddriver);

	public void whenUnhandledAlertIsFound(Alert alert);

	public void whenUnhandledWindowIsAlreadyClosed(WebDriver weddriver);

	public void whenUnhandledWindowIsFound(WebDriver weddriver);

	public void whenUnhandledWindowIsNotClosed(WebDriver webdriver);
}
