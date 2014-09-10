package org.arachnidium.core.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.arachnidium.util.proxy.EnhancedProxyFactory;
import org.openqa.selenium.WebDriver;

/**
 * This proxy factory creates instances of {@link WebdriverComponent}
 * subclasses
 */
public final class ComponentFactory {
	
	/**
	 * Creates objects of the class which extends {@link WebdriverComponent} using 
	 * @see WebdriverComponent#WebdriverComponent(WebDriver) as the default constructor
	 * 
	 * @param required Class which extends {@link WebdriverComponent}
	 * @param driver {@link WebDriver} instance which has to be wrapped
	 * @return instance required class which extends {@link WebdriverComponent}
	 */
	public static <T extends WebdriverComponent> T getComponent(
			Class<T> required, final WebDriver driver) {
		return getComponent(required, driver, new Class<?>[] {},
				new Object[] {});
	}

	/**
	 * Creates objects of the class which extends {@link WebdriverComponent} using 
	 * constructor that differs  from @see WebdriverComponent#WebdriverComponent(WebDriver).
	 * Although, this constructor should have {@link WebDriver} as the first parameter.
	 * 
	 * @param required Class which extends {@link WebdriverComponent}
	 * @param driver driver {@link WebDriver} instance which has to be wrapped
	 * @param types is a Class[] without {@link WebDriver.class}
	 * @param args is a Object[] without {@link WebDriver} instance
	 * @return
	 */
	public static <T extends WebdriverComponent> T getComponent(
			Class<T> required, final WebDriver driver, Class<?>[] types,
			Object[] args) {
		List<Class<?>> typeList = new ArrayList<Class<?>>() {
			private static final long serialVersionUID = 1L;
			{
				add(WebDriver.class);
			}
		};
		typeList.addAll(Arrays.asList(types));

		List<Object> valueList = new ArrayList<Object>() {
			private static final long serialVersionUID = 1L;

			{
				add(driver);
			}
		};
		valueList.addAll(Arrays.asList(args));
		return EnhancedProxyFactory.getProxy(required,
				typeList.toArray(new Class<?>[] {}),
				valueList.toArray(new Object[] {}), new ComponentInterceptor());
	}

	private ComponentFactory() {
		super();
	}
}
