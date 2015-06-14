package com.github.arachnidium.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import com.github.arachnidium.core.components.common.TimeOut;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

import com.github.arachnidium.util.inheritance.MethodInheritanceUtil;

class SearchContextProxyHandler implements InvocationHandler {

	private final Handle handle;
	private final static List<Class<?>> classesThatRequireFocusOnTheHandle = new ArrayList<Class<?>>() {
		private static final long serialVersionUID = 1L;
		{
			add(WebDriver.class);
			add(WebElement.class);
			add(SearchContext.class);
		}

	};

	private void changeTimeOutAndRun(Runnable runnable) {
		TimeOut timeOut = handle.driverEncapsulation.getTimeOut();
		long timeOutValue = timeOut.getImplicitlyWaitTimeOut();
		TimeUnit timeUnit = timeOut.getImplicitlyWaitTimeUnit();
		try {
			timeOut.implicitlyWait(0, TimeUnit.SECONDS);
			runnable.run();
		} finally {
			timeOut.implicitlyWait(timeOutValue, timeUnit);
		}
	}

	SearchContextProxyHandler(Handle handle) {
		this.handle = handle;
	}

	@Override
	public Object invoke(final Object o, final Method m, Object[] args)
			throws Throwable {

		final WebDriver driver = handle.driverEncapsulation.getWrappedDriver();
		if (MethodInheritanceUtil.isOverriddenFrom(m, WrapsDriver.class))
			return driver;

		if (MethodInheritanceUtil.isOverriddenFrom(m, HasCapabilities.class))
			return ((HasCapabilities) driver).getCapabilities();

		if (MethodInheritanceUtil.isOverriddenFromAny(m,
				classesThatRequireFocusOnTheHandle))
			handle.switchToMe();

		if (MethodInheritanceUtil.isOverriddenFrom(m, WebElement.class)) {
			final List<WebElement> elements = new ArrayList<>();
			changeTimeOutAndRun(() -> elements.addAll(driver
					.findElements(handle.by)));
			if (elements.size() == 0)
				throw new NoSuchElementException(
						"There is no element that could be found using locator strategy "
								+ handle.by.toString());

			return m.invoke(elements.get(0), args);
		}

		if (MethodInheritanceUtil.isOverriddenFrom(m, SearchContext.class)) {
			final List<Object> result = new ArrayList<>();
			changeTimeOutAndRun(() -> {
				try {
					if (o instanceof WebDriver)
						result.add(m.invoke(driver,
								new Object[] { (By) args[0] }));
					if (o instanceof WebElement)
						result.add(m.invoke(driver.findElement(handle.by),
								new Object[] { (By) args[0] }));
				} catch (InvocationTargetException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			});
			return result.get(0);
		}

		if (MethodInheritanceUtil.isOverriddenFrom(m, WebDriver.class))
			return m.invoke(driver, args);

		return m.invoke(o, args);

	}

}
