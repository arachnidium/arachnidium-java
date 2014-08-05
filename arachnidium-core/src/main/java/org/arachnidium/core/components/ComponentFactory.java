package org.arachnidium.core.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.arachnidium.util.proxy.EnhancedProxyFactory;
import org.openqa.selenium.WebDriver;

public final class ComponentFactory {
	public static <T extends WebdriverComponent> T getComponent(
			Class<T> required, final WebDriver driver) {
		return getComponent(required, driver, new Class<?>[] {},
				new Object[] {});
	}

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
