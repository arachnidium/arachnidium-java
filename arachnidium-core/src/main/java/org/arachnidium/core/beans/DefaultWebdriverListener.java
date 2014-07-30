package org.arachnidium.core.beans;

import java.util.concurrent.TimeUnit;

import org.arachnidium.core.eventlisteners.IExtendedWebDriverEventListener;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;

@Aspect
class DefaultWebdriverListener implements
		IExtendedWebDriverEventListener {

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterAlertAccept(WebDriver driver, Alert alert) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterAlertDismiss(WebDriver driver, Alert alert) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterSubmit(WebDriver driver, WebElement element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWebDriverSetTimeOut(WebDriver driver, Timeouts timeouts,
			long timeOut, TimeUnit timeUnit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeAlertAccept(WebDriver driver, Alert alert) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeAlertDismiss(WebDriver driver, Alert alert) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeSubmit(WebDriver driver, WebElement element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeWebDriverSetTimeOut(WebDriver driver, Timeouts timeouts,
			long timeOut, TimeUnit timeUnit) {
		// TODO Auto-generated method stub

	}

}
