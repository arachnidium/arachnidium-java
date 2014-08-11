package org.arachnidium.core.eventlisteners;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * @author s.tihomirov For some functions that are not declared in
 *         WebDriverEventListener
 */
public interface IWebDriverEventListener extends WebDriverEventListener {
	public void afterAlertAccept(WebDriver driver, Alert alert);

	public void afterAlertDismiss(WebDriver driver, Alert alert);

	public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys);

	//
	public void afterSubmit(WebDriver driver, WebElement element);

	public void beforeAlertAccept(WebDriver driver, Alert alert);

	public void beforeAlertDismiss(WebDriver driver, Alert alert);

	public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys);

	//
	public void beforeSubmit(WebDriver driver, WebElement element);
	
	public void beforeFindBy(String byString, WebElement element, WebDriver driver); 
	public void afterFindBy(String byString, WebElement element, WebDriver driver); 
	
	public void beforeQuit(WebDriver driver);
}
