package com.github.arachnidium.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

class SearchContextProxyHandler implements InvocationHandler {
	
	private final Handle handle;
	
	SearchContextProxyHandler(Handle handle){
		this.handle = handle;
	}

	@Override
	public Object invoke(Object o, Method m, Object[] args)
			throws Throwable {
		Class<?> declaredBy = m.getDeclaringClass(); 
		
		if (declaredBy.equals(WrapsDriver.class))
			return handle.driverEncapsulation.getWrappedDriver();
		
		if (declaredBy.equals(HasCapabilities.class))
			return ((HasCapabilities) handle.driverEncapsulation.
					getWrappedDriver()).getCapabilities();
		
		if (declaredBy.equals(WebDriver.class) || declaredBy.equals(WebElement.class)
				|| declaredBy.equals(WrapsElement.class)
				|| declaredBy.equals(SearchContext.class))
			handle.switchToMe();		
		
		Object webDriverOrElement = null;
		
		if (declaredBy.equals(WebDriver.class))
			webDriverOrElement = handle.driverEncapsulation.getWrappedDriver();
		
		if (declaredBy.equals(WrapsElement.class))
			return handle.driverEncapsulation.
					getWrappedDriver().findElement(handle.by);
		
		if (declaredBy.equals(WebElement.class))
			webDriverOrElement = handle.driverEncapsulation.
					getWrappedDriver().findElement(handle.by);
		
		if (declaredBy.equals(SearchContext.class))
			return m.invoke(handle.driverEncapsulation.
					getWrappedDriver(), new Object[] {handle.returnBy((By) args[0])});
		
		if (webDriverOrElement != null)
			return m.invoke(webDriverOrElement, args);
		
		return m.invoke(o, args);
			
	}

}
