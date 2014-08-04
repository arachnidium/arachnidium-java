package org.arachnidium.core.eventlisteners;

import org.arachnidium.core.interfaces.IWebElementHighlighter;
import org.arachnidium.util.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Deprecated //TODO Refactor/Remove
public final class DefaultWebdriverListener implements
IWebDriverEventListener {

	private IWebElementHighlighter highLighter;

	private String addToDescription(WebElement element, String attribute,
			String description) {
		try {
			if (element.getAttribute(attribute) == null)
				return description;
			if (element.getAttribute(attribute).equals(""))
				return description;
			description += " " + attribute + ": "
					+ String.valueOf(element.getAttribute(attribute));
		} catch (Exception e) {
		}
		return description;
	}

	@Override
	public void afterAlertAccept(WebDriver driver, Alert alert) {
		Log.message("Alert has been accepted");
	}

	@Override
	public void afterAlertDismiss(WebDriver driver, Alert alert) {
		Log.message("Alert has been dismissed");
	}

	@Override
	public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys) {
		Log.message("String " + keys + " has been sent to alert");
	}

	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
		highlightElementAndLogAction(arg0, arg1,
				"State after element value was changed.");
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
	public void afterSubmit(WebDriver driver, WebElement element) {
		Log.message("Submit has been performed successfully");
	}

	@Override
	public void beforeAlertAccept(WebDriver driver, Alert alert) {
		Log.message("Attempt to accept alert...");
	}

	@Override
	public void beforeAlertDismiss(WebDriver driver, Alert alert) {
		Log.message("Attempt to dismiss the alert...");

	}

	@Override
	public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys) {
		Log.message("Attemt to send string " + keys + " to alert...");
	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		highlightElementAndLogAction(arg0, arg1,
				"State before element value will be changed.");
	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		highlightElementAndLogAction(arg0, arg1,
				"State before element will be clicked on.");
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
	public void beforeSubmit(WebDriver driver, WebElement element) {
		highlightElementAndLogAction(element, driver,
				"State before submit will be performed by element: ");
	}

	private String elementDescription(WebElement element) {
		String description = "";
		if (element == null)
			return description;

		if (!String.valueOf(element.getTagName()).equals(""))
			description += "tag:" + String.valueOf(element.getTagName());
		description = addToDescription(element, "id", description);
		description = addToDescription(element, "name", description);
		if (!"".equals(element.getText()))
			description += " ('" + String.valueOf(element.getText()) + "')";
		if (!description.equals(""))
			description = " Element is: " + description;

		return description;
	}

	private void highlightElementAndLogAction(WebElement arg0, WebDriver arg1,
			String logMessage) {
		String elementDescription = elementDescription(arg0);
		if (highLighter != null) {
			highLighter.highlightAsInfo(arg1, arg0, logMessage
					+ elementDescription);
			return;
		}
		Log.message(logMessage + elementDescription);
	}

	@Override
	public void onException(Throwable arg0, WebDriver arg1) {
		Log.debug("An exception has been caught out."
				+ arg0.getClass().getName() + ":" + arg0.getMessage());
	}

	public void setHighLighter(IWebElementHighlighter highLighter) {
		this.highLighter = highLighter;
	}

	@Override
	public void beforeFindBy(String byString, WebElement element,
			WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterFindBy(String byString, WebElement element,
			WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

}
