package org.arachnidium.core.components.common;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Awaiting extends WebdriverComponent {

	public Awaiting(WebDriver driver) {
		super(driver);
		delegate = this;
	}

	@SuppressWarnings("unchecked")
	public <T> T awaitCondition(long secTimeOut, ExpectedCondition<?> condition)
			throws TimeoutException {
		return (T) new WebDriverWait(driver, secTimeOut).until(condition);
	}

	@SuppressWarnings("unchecked")
	public <T> T awaitCondition(long secTimeOut, long sleepInMillis,
			ExpectedCondition<?> condition) throws TimeoutException {
		return (T) new WebDriverWait(driver, secTimeOut, sleepInMillis)
		.until(condition);
	}

}
