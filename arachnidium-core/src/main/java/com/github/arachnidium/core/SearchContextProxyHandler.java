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

import com.github.arachnidium.util.inheritance.MethodInheritanceUtil;

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
		
		WebDriver driver = handle.driverEncapsulation.getWrappedDriver();
		if (MethodInheritanceUtil.isOverriddenFrom(m, WrapsDriver.class))
			return driver;
		
		if (MethodInheritanceUtil.isOverriddenFrom(m, HasCapabilities.class))
			return ((HasCapabilities) driver).getCapabilities();
		
		if (MethodInheritanceUtil.isOverriddenFromAny(m, 
				classesThatRequireFocusOnTheHandle))
			handle.switchToMe();		
		
		Object webDriverOrElement = null;
		
		if (MethodInheritanceUtil.isOverriddenFrom(m, WebDriver.class))
			webDriverOrElement = driver;
		
		if (MethodInheritanceUtil.isOverriddenFrom(m, WrapsElement.class))
			return driver.findElement(handle.by);
		
		if (MethodInheritanceUtil.isOverriddenFrom(m, WebElement.class))
			webDriverOrElement = driver.findElement(handle.by);
		
		if (MethodInheritanceUtil.isOverriddenFrom(m, 
				SearchContext.class))
			return m.invoke(driver, new Object[] {handle.returnBy((By) args[0])});
		
		if (webDriverOrElement != null)
			return m.invoke(webDriverOrElement, args);
		
		return m.invoke(o, args);
			
	}

}
