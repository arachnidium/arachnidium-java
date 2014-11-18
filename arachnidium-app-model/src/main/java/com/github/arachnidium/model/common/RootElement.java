package com.github.arachnidium.model.common;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;

/**
 * This object performs the searching of the desired element which becomes the
 * root element in the chain of further searches.
 */
class RootElement implements WrapsElement {
	static final String ROOT_ELEMENT_FIELD_NAME = "rootElement";

	final SearchContext context;
	final By by;
	long timeValue;
	TimeUnit timeUnit;

	/**
	 * The wrapped root element will be found by given parameters
	 */
	// Function
	RootElement(SearchContext searchContext, By by, long timeValue,
			TimeUnit timeUnit) {
		this.context = searchContext;
		this.by = by;
		setTimeValue(timeValue);
		setTimeUnit(timeUnit);
	}

	void setTimeValue(long timeValue) {
		this.timeValue = timeValue;
	}

	void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	@Override
	public WebElement getWrappedElement() {
		Function<By, WebElement> waitingAlgorithm = input -> {
			try {
				return context.findElement(by);
			} catch (StaleElementReferenceException | NoSuchElementException ignored) {
				return null;
			}
		};
		try {
			FluentWait<By> wait = new FluentWait<By>(by);
			wait.withTimeout(timeValue, timeUnit);
			return wait.until(waitingAlgorithm);
		} catch (TimeoutException e) {
			throw new NoSuchElementException(
					"Cann't locate the root element by " + by.toString());
		}
	}
}
