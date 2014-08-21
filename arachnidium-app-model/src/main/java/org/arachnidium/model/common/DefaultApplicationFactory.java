package org.arachnidium.model.common;

import java.net.URL;

import net.sf.cglib.proxy.MethodInterceptor;

import org.arachnidium.core.Handle;
import org.arachnidium.core.Manager;
import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.settings.supported.ESupportedDrivers;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.proxy.EnhancedProxyFactory;
import org.openqa.selenium.Capabilities;

/**
 * Utility class that contains methods which create {@link Application}
 * instances
 */
public class DefaultApplicationFactory {

	/**
	 * Creation of any decomposable part of application
	 */
	protected static <T extends IDecomposable> T get(Class<T> partClass,
			Object[] paramValues) {
		T decomposable = EnhancedProxyFactory.getProxy(partClass,
				ModelSupportUtil.getParameterClasses(paramValues, partClass), paramValues,
				new InteractiveInterceptor());
		return decomposable;
	}

	/**
	 * Common method that creates an instance of any application with default
	 * configuration
	 */
	protected static <T extends Application<?, ?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			MethodInterceptor mi) {
		return getApplication(handleManagerClass, appClass,
				new Class<?>[] { Configuration.class },
				new Object[] { Configuration.byDefault }, mi);
	}

	/**
	 * Common method that creates an instance of any application with defined
	 * configuration
	 */
	protected static <T extends Application<?, ?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			Configuration config, MethodInterceptor mi) {
		return getApplication(handleManagerClass, appClass,
				new Class<?>[] { Configuration.class },
				new Object[] { config }, mi);
	}

	/**
	 * Common method that creates an instance of any application with defined
	 * webdriver
	 */
	protected static <T extends Application<?, ?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			ESupportedDrivers supportedDriver, MethodInterceptor mi) {
		return getApplication(handleManagerClass, appClass,
				new Class<?>[] { ESupportedDrivers.class },
				new Object[] { supportedDriver }, mi);
	}

	/**
	 * Common method that creates an instance of any application with defined
	 * webdriver and its capabilities
	 */
	protected static <T extends Application<?, ?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			MethodInterceptor mi) {
		return getApplication(handleManagerClass, appClass, new Class<?>[] {
				ESupportedDrivers.class, Capabilities.class }, new Object[] {
				supportedDriver, capabilities }, mi);
	}

	/**
	 * Common method that creates an instance of any application with defined
	 * webdriver, capabilities and URL to remote server
	 */
	protected static <T extends Application<?, ?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			URL remoteAddress, MethodInterceptor mi) {
		return getApplication(handleManagerClass, appClass, new Class<?>[] {
				ESupportedDrivers.class, Capabilities.class, URL.class },
				new Object[] { supportedDriver, capabilities, remoteAddress },
				mi);
	}

	/**
	 * Common method that creates an instance of any application with defined
	 * webdriver and URL to remote server
	 */
	protected static <T extends Application<?, ?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			ESupportedDrivers supportedDriver, URL remoteAddress,
			MethodInterceptor mi) {
		return getApplication(handleManagerClass, appClass, new Class<?>[] {
				ESupportedDrivers.class, URL.class }, new Object[] {
				supportedDriver, remoteAddress }, mi);
	}

	/**
	 * Common method that creates an instance of any application with externally
	 * instantiated {@link WebDriverEncapsulation}
	 */
	protected static <T extends Application<?, ?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			WebDriverEncapsulation wdEncapsulation, MethodInterceptor mi) {
		Handle h = ModelSupportUtil.getTheFirstHandle(handleManagerClass, wdEncapsulation);
		return EnhancedProxyFactory.getProxy(appClass,
				ModelSupportUtil.getParameterClasses(new Object[] { h }, appClass),
				new Object[] { h }, mi);

	}

	protected static WebDriverEncapsulation getWebDriverEncapsulation(
			Application<?, ?> app) {
		return app.getWebDriverEncapsulation();
	}

	/**
	 * Instantiates {@link Application} by initial parameters
	 */
	private static <T extends Application<?, ?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			Class<?>[] initaialParameterClasses,
			Object[] initaialParameterValues, MethodInterceptor mi) {
		Handle h = null;
		try {
			h = ModelSupportUtil.getTheFirstHandle(handleManagerClass, initaialParameterClasses,
					initaialParameterValues);
			return EnhancedProxyFactory.getProxy(appClass,
					ModelSupportUtil.getParameterClasses(new Object[] { h }, appClass),
					new Object[] { h }, mi);
		} catch (Exception e) {
			if (h != null) {
				h.getDriverEncapsulation().destroy();
			}
			throw new RuntimeException(e);
		}
	
	}

}
