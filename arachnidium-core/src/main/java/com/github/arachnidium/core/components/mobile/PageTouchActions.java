package com.github.arachnidium.core.components.mobile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.RemoteTouchScreen;

import com.github.arachnidium.core.components.WebdriverComponent;

/**
 * {@link TouchScreen} default implementor
 *
 */
public abstract class PageTouchActions extends WebdriverComponent implements
		TouchScreen {
	private final String GET_EXECUTE_METHOD = "getExecuteMethod";
	private final Class<?>[] EMPTY_ARGS         = new Class<?>[]{};
	private final Object[] EMPTY_VALUES         = new Object[]{};
	
	public PageTouchActions(WebDriver driver) throws Exception, InvocationTargetException {
		super(driver);
		Method getExecuteMethod = driver.getClass().getMethod(GET_EXECUTE_METHOD, EMPTY_ARGS);
		getExecuteMethod.setAccessible(true);
		
		delegate = new RemoteTouchScreen((ExecuteMethod) getExecuteMethod.invoke(this.driver, EMPTY_VALUES));
	}

}
