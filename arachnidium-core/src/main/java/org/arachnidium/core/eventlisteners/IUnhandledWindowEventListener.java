package org.arachnidium.core.eventlisteners;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public interface IUnhandledWindowEventListener {
	public void whenUnhandledWindowIsFound(WebDriver weddriver);
	public void whenUnhandledAlertIsFound(Alert alert);	
	public void whenUnhandledWindowIsNotClosed(WebDriver webdriver);
	public void whenUnhandledWindowIsAlreadyClosed(WebDriver weddriver);
	public void whenNoAlertThere(WebDriver weddriver);
}
