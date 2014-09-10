package org.arachnidium.core.eventlisteners;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * This interface extends {@link WebDriverEventListener}
 * There are some methods that weren't declared in super interface 
 */
public interface IWebDriverEventListener extends WebDriverEventListener {
    /**
     * This action will be performed each time after 
     * {@link Alert} is accepted
     * @param driver Instance of {@link WebDriver}
     * @param alert {@link Alert} which is accepted
     */
	public void afterAlertAccept(WebDriver driver, Alert alert);

    /**
     * This action will be performed each time after 
     * {@link Alert} is dismissed
     * @param driver Instance of {@link WebDriver}
     * @param alert {@link Alert} which is dismissed
     */	
	public void afterAlertDismiss(WebDriver driver, Alert alert);

    /**
     * This action will be performed each time after 
     * keys are sent to {@link Alert}
     * @param driver Instance of {@link WebDriver}
     * @param alert {@link Alert} which receives keys
     * @param keys Keys which are sent
     */		
	public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys);

	/**
	 * This action will be performed each time after
	 * {@link WebElement} is submitted
	 * 
	 * @param driver Instance of {@link WebDriver}
     * @param {@link WebElement} which is submitted
	 */
	public void afterSubmit(WebDriver driver, WebElement element);

    /**
     * This action will be performed each time before 
     * {@link Alert} will be accepted
     * @param driver Instance of {@link WebDriver}
     * @param alert {@link Alert} which is accepted
     */	
	public void beforeAlertAccept(WebDriver driver, Alert alert);

    /**
     * This action will be performed each time abefore 
     * {@link Alert} will be dismissed
     * @param driver Instance of {@link WebDriver}
     * @param alert {@link Alert} which is dismissed
     */		
	public void beforeAlertDismiss(WebDriver driver, Alert alert);

    /**
     * This action will be performed each time before 
     * keys will be sent to {@link Alert}
     * @param driver Instance of {@link WebDriver}
     * @param alert {@link Alert} which receives keys
     * @param keys Keys which will be sent
     */			
	public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys);

	/**
	 * This action will be performed each time before
	 * {@link WebElement} will be submitted
	 * 
	 * @param driver Instance of {@link WebDriver}
     * @param {@link WebElement} will be submitted
	 */
	public void beforeSubmit(WebDriver driver, WebElement element);
	
	/**
	 * This action will be performed each time before search by string
	 * locator is started
	 * 
	 * @param byString String locator
	 * @param element  The root {@link WebElement}
	 * @param driver
	 */
	public void beforeFindBy(String byString, WebElement element, WebDriver driver); 
	
	/**
	 * This action will be performed each time after search by string
	 * locator is finished
	 * 
	 * @param byString String locator
	 * @param element  The root {@link WebElement}
	 * @param driver
	 */	
	public void afterFindBy(String byString, WebElement element, WebDriver driver); 
	
	/**
	 * This action will be performed 
	 * before {@link WebDriver} is quieted
	 */
	public void beforeQuit(WebDriver driver);
}
