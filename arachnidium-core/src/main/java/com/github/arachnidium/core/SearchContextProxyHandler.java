package com.github.arachnidium.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

class SearchContextProxyHandler implements InvocationHandler {
	
	private final Handle handle;
	private final static List<Class<?>> classesThatRequireFocusOnTheHandle = new ArrayList<Class<?>>() {
		private static final long serialVersionUID = 1L;
		{
			add(WebDriver.class);
			add(WebElement.class);
			add(WrapsElement.class);
			add(SearchContext.class);
		}

	};
	
	SearchContextProxyHandler(Handle handle){
		this.handle = handle;
	}

	@Override
	public Object invoke(Object o, Method m, Object[] args)
			throws Throwable {
		Class<?> declaredBy = m.getDeclaringClass(); 
		
		WebDriver driver = handle.driverEncapsulation.getWrappedDriver();
		if (declaredBy.equals(WrapsDriver.class))
			return driver;
		
		if (declaredBy.equals(HasCapabilities.class))
			return ((HasCapabilities) driver).getCapabilities();
		
		if (classesThatRequireFocusOnTheHandle.contains(declaredBy))
			handle.switchToMe();		
		
		Object webDriverOrElement = null;
		
		if (declaredBy.equals(WebDriver.class))
			webDriverOrElement = driver;
		
		if (declaredBy.equals(WrapsElement.class))
			return driver.findElement(handle.by);
		
		if (declaredBy.equals(WebElement.class))
			webDriverOrElement = driver.findElement(handle.by);
		
		if (declaredBy.equals(SearchContext.class))
			return m.invoke(driver, new Object[] {handle.returnBy((By) args[0])});
		
		if (webDriverOrElement != null)
			return m.invoke(webDriverOrElement, args);
		
		return m.invoke(o, args);
			
	}

}
