package org.arachnidium.model.common;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Arrays;

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

	private static Class<?>[] getParameterClasses(Object[] paramerers,
			Class<?> requiredClass) {

		Class<?>[] givenParameters = new Class<?>[paramerers.length];
		for (int i = 0; i < paramerers.length; i++) {
			givenParameters[i] = paramerers[i].getClass();
		}

		Constructor<?>[] declaredConstructors = requiredClass
				.getDeclaredConstructors();
		for (Constructor<?> constructor : declaredConstructors) {
			Class<?>[] declaredParameters = constructor.getParameterTypes();

			if (declaredParameters.length != givenParameters.length) {
				continue;
			}

			boolean isMatch = true;
			for (int i = 0; i < declaredParameters.length; i++) {
				if (!declaredParameters[i].isAssignableFrom(givenParameters[i])) {
					isMatch = false;
					break;
				}
			}

			if (isMatch) {
				return declaredParameters;
			}
		}
		throw new RuntimeException(new NoSuchMethodException(
				"There is no suitable constructor! Given parameters: "
						+ Arrays.asList(givenParameters).toString() + ". "
						+ "Class is " + requiredClass.getName()));
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
			h = getTheFirstHandle(handleManagerClass, initaialParameterClasses,
					initaialParameterValues);
			return EnhancedProxyFactory.getProxy(appClass,
					getParameterClasses(new Object[] { h }, appClass),
					new Object[] { h }, mi);
		} catch (Exception e) {
			if (h != null) {
				h.getDriverEncapsulation().destroy();
			}
			throw new RuntimeException(e);
		}

	}

	/**
	 * Creation of any decomposable part of application
	 */
	protected static <T extends IDecomposable> T get(Class<T> partClass,
			Object[] paramValues) {
		T decomposable = EnhancedProxyFactory.getProxy(partClass,
				getParameterClasses(paramValues, partClass), paramValues,
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
		Handle h = getTheFirstHandle(handleManagerClass, wdEncapsulation);
		return EnhancedProxyFactory.getProxy(appClass,
				getParameterClasses(new Object[] { h }, appClass),
				new Object[] { h }, mi);

	}

	private static Handle getTheFirstHandle(
			Class<? extends Manager<?>> handleManagerClass,
			Class<?>[] wdEncapsulationParams, Object[] wdEncapsulationParamVals) {
		try {
			Constructor<?> wdeC = WebDriverEncapsulation.class
					.getConstructor(wdEncapsulationParams);
			WebDriverEncapsulation wdeInstance = (WebDriverEncapsulation) wdeC
					.newInstance(wdEncapsulationParamVals);
			return getTheFirstHandle(handleManagerClass, wdeInstance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Handle getTheFirstHandle(
			Class<? extends Manager<?>> handleManagerClass,
			WebDriverEncapsulation wdeInstance) {
		try {
			Constructor<?> c = handleManagerClass
					.getConstructor(new Class<?>[] { WebDriverEncapsulation.class });
			Manager<?> m = (Manager<?>) c
					.newInstance(new Object[] { wdeInstance });

			return m.getHandle(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static WebDriverEncapsulation getWebDriverEncapsulation(
			Application<?, ?> app) {
		return app.getWebDriverEncapsulation();
	}

}
