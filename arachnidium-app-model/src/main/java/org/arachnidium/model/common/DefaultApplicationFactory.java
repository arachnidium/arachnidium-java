package org.arachnidium.model.common;

import java.lang.reflect.Constructor;
import java.net.URL;

import net.sf.cglib.proxy.MethodInterceptor;

import org.arachnidium.core.Handle;
import org.arachnidium.core.Manager;
import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.settings.supported.ESupportedDrivers;
import org.arachnidium.model.abstractions.ModelObjectInterceptor;
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

		Class<?>[] expectedParameters = new Class<?>[paramerers.length];
		for (int i = 0; i < paramerers.length; i++) {
			expectedParameters[i] = paramerers[i].getClass();
		}

		Constructor<?>[] declaredConstructors = requiredClass
				.getDeclaredConstructors();
		for (Constructor<?> constructor : declaredConstructors) {
			Class<?>[] declaredParameters = constructor.getParameterTypes();

			if (declaredParameters.length != expectedParameters.length) {
				continue;
			}

			boolean isMatch = true;
			for (int i = 0; i < declaredParameters.length; i++) {
				if (!declaredParameters[i]
						.isAssignableFrom(expectedParameters[i])) {
					isMatch = false;
					break;
				}
			}

			if (isMatch) {
				return declaredParameters;
			}
		}
		throw new RuntimeException(new NoSuchMethodException(
				"There is no suitable constructor! Parameters: "
						+ paramerers.toString() + ", their classes "
						+ expectedParameters.toString() + ". " + "Class is "
						+ requiredClass.getName()));
	}	
	
	/**
	 * Creation of any decomposable part of application
	 */
	protected static <T extends IDecomposable> T get(Class<T> partClass,
			Object[] paramValues) {
		T decomposable = EnhancedProxyFactory.getProxy(partClass, getParameterClasses(paramValues, partClass),
				paramValues, getInteractiveInterceptor());
		return decomposable;
	}

	private static <T extends MethodInterceptor> T getAppInterceptor() {
		return getInterceptorFromThreadLocal(definedInterceptorForEntities,
				ModelObjectInterceptor.class);
	}

	/**
	 * Common method that creates an instance of any application with default
	 * configuration
	 */
	protected static <T extends Application<?, ?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass) {
		Handle h = getTheFirstHandle(handleManagerClass,
				new Class<?>[] { Configuration.class },
				new Object[] { Configuration.byDefault });
		return EnhancedProxyFactory.getProxy(appClass,
				getParameterClasses(new Object[] { h }, appClass),
				new Object[] { h }, getAppInterceptor());
	}

	/**
	 * Common method that creates an instance of any application with defined
	 * configuration
	 */
	protected static <T extends Application<?,?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			Configuration config) {
		Handle h = getTheFirstHandle(handleManagerClass,
				new Class<?>[] { Configuration.class }, new Object[] { config });
		return EnhancedProxyFactory.getProxy(appClass,
				getParameterClasses(new Object[] { h }, appClass),
				new Object[] { h }, getAppInterceptor());
	}

	/**
	 * Common method that creates an instance of any application with defined
	 * webdriver
	 */
	protected static <T extends Application<?,?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			ESupportedDrivers supportedDriver) {
		Handle h = getTheFirstHandle(handleManagerClass,
				new Class<?>[] { ESupportedDrivers.class },
				new Object[] { supportedDriver });
		return EnhancedProxyFactory.getProxy(appClass,
				getParameterClasses(new Object[] { h }, appClass),
				new Object[] { h }, getAppInterceptor());
	}

	/**
	 * Common method that creates an instance of any application with defined
	 * webdriver and its capabilities
	 */
	protected static <T extends Application<?,?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities) {
		Handle h = getTheFirstHandle(handleManagerClass, new Class<?>[] {
				ESupportedDrivers.class, Capabilities.class }, new Object[] {
				supportedDriver, capabilities });
		return EnhancedProxyFactory.getProxy(appClass,
				getParameterClasses(new Object[] { h }, appClass),
				new Object[] { h }, getAppInterceptor());
	}

	/**
	 * Common method that creates an instance of any application with defined
	 * webdriver, capabilities and URL to remote server
	 */
	protected static <T extends Application<?,?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			URL remoteAddress) {
		Handle h = getTheFirstHandle(handleManagerClass, new Class<?>[] {
				ESupportedDrivers.class, Capabilities.class, URL.class },
				new Object[] { supportedDriver, capabilities, remoteAddress });
		return EnhancedProxyFactory.getProxy(appClass,
				getParameterClasses(new Object[] { h }, appClass),
				new Object[] { h }, getAppInterceptor());
	}

	/**
	 * Common method that creates an instance of any application with defined
	 * webdriver and URL to remote server
	 */
	protected static <T extends Application<?,?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			ESupportedDrivers supportedDriver, URL remoteAddress) {
		Handle h = getTheFirstHandle(handleManagerClass, new Class<?>[] {
				ESupportedDrivers.class, URL.class }, new Object[] {
				supportedDriver, remoteAddress });
		return EnhancedProxyFactory.getProxy(appClass,
				getParameterClasses(new Object[] { h }, appClass),
				new Object[] { h }, getAppInterceptor());
	}

	/**
	 * Common method that creates an instance of any application with externally
	 * instantiated {@link WebDriverEncapsulation}
	 */
	protected static <T extends Application<?,?>> T getApplication(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			WebDriverEncapsulation wdEncapsulation) {
		Handle h = getTheFirstHandle(handleManagerClass, wdEncapsulation);
		return EnhancedProxyFactory.getProxy(appClass,
				getParameterClasses(new Object[] { h }, appClass),
				new Object[] { h }, getAppInterceptor());
	}

	private static <T extends MethodInterceptor> T getInteractiveInterceptor() {
		return getInterceptorFromThreadLocal(definedInteractiveInterceptor,
				InteractiveInterceptor.class);
	}

	@SuppressWarnings("unchecked")
	private static <T extends MethodInterceptor> T getInterceptorFromThreadLocal(
			ThreadLocal<Class<? extends MethodInterceptor>> from,
			Class<? extends MethodInterceptor> defaultInterceptorClass) {
		try {
			if (from.get() == null)
				return (T) defaultInterceptorClass.newInstance();
			return (T) from.get().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static Handle getTheFirstHandle(
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

	protected static Handle getTheFirstHandle(
			Class<? extends Manager<?>> handleManagerClass,
			WebDriverEncapsulation wdeInstance) {
		try {
			Constructor<?> c = handleManagerClass
					.getConstructor(new Class<?>[] { WebDriverEncapsulation.class });
			Manager<?> m = (Manager<?>) c.newInstance(new Object[] { wdeInstance });

			return m.getHandle(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static WebDriverEncapsulation getWebDriverEncapsulation(
			Application<?,?> app) {
		return app.getWebDriverEncapsulation();
	}

	/**
	 * Resets iterceptor class for {@link Application}
	 */
	public static void resetApplicationInterceptor(
			Class<? extends ModelObjectInterceptor> interceptorClass) {
		resetInterceptor(definedInterceptorForEntities, interceptorClass);
	}

	/**
	 * Resets iterceptor class for {@link FunctionalPart}
	 */
	public static void resetInteractiveInterceptor(
			Class<? extends InteractiveInterceptor> interceptorClass) {
		resetInterceptor(definedInteractiveInterceptor, interceptorClass);
	}

	private static void resetInterceptor(
			ThreadLocal<Class<? extends MethodInterceptor>> to,
			Class<? extends MethodInterceptor> interceptorClass) {
		to.set(interceptorClass);
	}

	/**
	 * An interceptor for {@link FunctionalPart} inheritor defined by user.
	 * Defined for each thread
	 */
	private static ThreadLocal<Class<? extends MethodInterceptor>> definedInteractiveInterceptor = new ThreadLocal<Class<? extends MethodInterceptor>>();

	/**
	 * An interceptor for {@link Application} inheritor defined by user. Defined
	 * for each thread
	 */
	private final static ThreadLocal<Class<? extends MethodInterceptor>> definedInterceptorForEntities = new ThreadLocal<Class<? extends MethodInterceptor>>();

}
