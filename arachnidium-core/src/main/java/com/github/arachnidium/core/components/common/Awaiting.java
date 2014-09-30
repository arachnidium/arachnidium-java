package com.github.arachnidium.core.components.common;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Just a simple tool to 
 * perform waiting for something by {@link ExpectedCondition}
 */
public class Awaiting {
    private final WebDriver driver;
    
	public Awaiting(WebDriver driver) {
		this.driver = driver;
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
