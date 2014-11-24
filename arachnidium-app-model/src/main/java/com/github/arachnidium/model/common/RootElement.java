package com.github.arachnidium.model.common;

import java.util.concurrent.TimeUnit;

import net.sf.cglib.proxy.MethodInterceptor;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.github.arachnidium.util.proxy.EnhancedProxyFactory;
import com.google.common.base.Function;

/**
 * This object performs the searching of the desired element which becomes the
 * root element in the chain of further searches.
 */
class RootElement implements WrapsElement {
	private final long POLLING_EVERY = 100; //MILLISECONDS
	By by;
	long timeValue;
	TimeUnit timeUnit;
	final FunctionalPart<?> functionalPart;

	/**
	 * The wrapped root element will be found by given parameters
	 */
	// Function
	RootElement(FunctionalPart<?> functionalPart, By by, long timeValue,
			TimeUnit timeUnit) {
		this.functionalPart = functionalPart;
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

	// this method returns the function which performs the waiting for the root
	// element
	private Function<By, WebElement> getWaitForTheRootElementFunction() {
		return input -> {
			try {
				return functionalPart.getWrappedDriver().findElement(by);
			} catch (StaleElementReferenceException | NoSuchElementException ignored) {
				return null;
			}
		};
	}

	private MethodInterceptor getRotElementMethodInterceptor() {
		return (obj, method, args, proxy) -> {
			WebDriver driver = functionalPart.getWrappedDriver();
			
			if (method.getReturnType().equals(WebDriver.class)){
				return driver;
			}
			
			Timeouts t = driver.manage().timeouts();
			t.implicitlyWait(0, TimeUnit.SECONDS);
			WebElement root = null;
			try {
				FluentWait<By> wait = new FluentWait<By>(by);
				wait.withTimeout(timeValue, timeUnit);
				wait.pollingEvery(POLLING_EVERY, TimeUnit.MILLISECONDS);
				root = wait.until(getWaitForTheRootElementFunction());
			} catch (TimeoutException e) {
				throw new NoSuchElementException(
						"Cann't locate the root element by " + by.toString(), e);
			} finally {
				t.implicitlyWait(timeValue, timeUnit);
			}

			return method.invoke(root, args);
		};
	}

	@Override
	public WebElement getWrappedElement() {
		functionalPart.switchToMe();
		return EnhancedProxyFactory.getProxy(RemoteWebElement.class,
				new Class[] {}, new Object[] {},
				getRotElementMethodInterceptor());
	}

	By getTheGivenByStrategy() {
		return by;
	}
	
	
	void changeByStrategy(By by){
		this.by = by;
	}
}
