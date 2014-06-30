package org.arachnidium.core.eventlisteners;

import java.util.concurrent.TimeUnit;

import org.arachnidium.core.interfaces.IWebElementHighlighter;
import org.arachnidium.util.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;

public final class DefaultWebdriverListener implements
		IExtendedWebDriverEventListener {

	private IWebElementHighlighter highLighter;

	private void highlightElementAndLogAction(WebElement arg0, WebDriver arg1, String logMessage){
		String elementDescription = elementDescription(arg0);
		if (highLighter!=null){
			highLighter.highlightAsInfo(arg1, arg0,
					logMessage
							+ elementDescription);
			return;
		}
		Log.message(logMessage + elementDescription);
	}
	
	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
		highlightElementAndLogAction(arg0, arg1,"State after element value was changed.");
	}

	@Override
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		Log.message("Click on element has been successfully performed!");
	}

	@Override
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		Log.debug("Searching for web element has been finished. Locator is "
				+ arg0.toString() + ". " + elementDescription(arg1));
	}

	@Override
	public void afterNavigateBack(WebDriver arg0) {
		Log.message("Current URL is  " + arg0.getCurrentUrl());
	}

	@Override
	public void afterNavigateForward(WebDriver arg0) {
		Log.message("Current URL is  " + arg0.getCurrentUrl());
	}

	@Override
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		Log.message("Current URL is " + arg1.getCurrentUrl());
	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		Log.debug("Javascript  " + arg0 + " has been executed successfully!");
	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		highlightElementAndLogAction(arg0, arg1,"State before element value will be changed.");
	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		highlightElementAndLogAction(arg0, arg1,"State before element will be clicked on.");
	}

	@Override
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		Log.debug("Searching for element by locator " + arg0.toString()
				+ " has been started");
	}

	@Override
	public void beforeNavigateBack(WebDriver arg0) {
		Log.message("Attempt to navigate to previous url. Current url is "
				+ arg0.getCurrentUrl());
	}

	@Override
	public void beforeNavigateForward(WebDriver arg0) {
		Log.message("Attempt to navigate to next url. Current url is "
				+ arg0.getCurrentUrl());
	}

	@Override
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		Log.message("Attempt to navigate to another url. Required url is "
				+ arg0);
	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) {
		Log.debug("Javascript execution has been started " + arg0);
	}

	@Override
	public void onException(Throwable arg0, WebDriver arg1) {
		Log.debug("An exception has been caught out.", arg0);
	}

	@Override
	public void beforeSubmit(WebDriver driver, WebElement element) {
		highlightElementAndLogAction(element, driver,"State before submit will be performed by element: ");
	}

	@Override
	public void afterSubmit(WebDriver driver, WebElement element) {
		Log.message("Submit has been performed successfully");
	}

	@Override
	public void beforeAlertDismiss(WebDriver driver, Alert alert) {
		Log.message("Attempt to dismiss the alert...");

	}

	@Override
	public void afterAlertDismiss(WebDriver driver, Alert alert) {
		Log.message("Alert has been dismissed");
	}

	@Override
	public void beforeAlertAccept(WebDriver driver, Alert alert) {
		Log.message("Attempt to accept alert...");
	}

	@Override
	public void afterAlertAccept(WebDriver driver, Alert alert) {
		Log.message("Alert has been accepted");
	}

	@Override
	public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys) {
		Log.message("Attemt to send string " + keys + " to alert...");
	}

	@Override
	public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys) {
		Log.message("String " + keys + " has been sent to alert");
	}

	@Override
	public void beforeWebDriverSetTimeOut(WebDriver driver, Timeouts timeouts,
			long timeOut, TimeUnit timeUnit) {
		Log.debug("Attempt to set time out. Value is " + Long.toString(timeOut)
				+ " time unit is " + timeUnit.toString());

	}

	@Override
	public void afterWebDriverSetTimeOut(WebDriver driver, Timeouts timeouts,
			long timeOut, TimeUnit timeUnit) {
		Log.message("Time out has been set. Value is " + Long.toString(timeOut)
				+ " time unit is " + timeUnit.toString());
	}

	private String addToDescription(WebElement element, String attribute,
			String description) {
		try {
			if (element.getAttribute(attribute) == null) {
				return description;
			}
			if (element.getAttribute(attribute).equals("")) {
				return description;
			}
			description += " " + attribute + ": "
					+ String.valueOf(element.getAttribute(attribute));
		} catch (Exception e) {
			Log.debug("Location is not supported by attribute '" + attribute
					+ "'...");
		}
		return description;
	}
	
	private String elementDescription(WebElement element) {
		String description = "";
		if (element == null) {
			return description;			
		}
		
		if (!String.valueOf(element.getTagName()).equals("")){
			description += "tag:" + String.valueOf(element.getTagName());
		}
		description = addToDescription(element, "id", description);
		description = addToDescription(element, "name", description);
		if (!"".equals(element.getText())){
			description += " ('" + String.valueOf(element.getText()) + "')";
		}
		if (!description.equals("")) {
			description = " Element is: " + description;
		}
		
		return description;
	}

	public void setHighLighter(IWebElementHighlighter highLighter) {
		this.highLighter = highLighter;
	}

}
