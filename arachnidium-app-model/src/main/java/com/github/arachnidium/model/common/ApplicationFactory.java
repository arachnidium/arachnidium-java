package com.github.arachnidium.model.common;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Arrays;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.Manager;
import com.github.arachnidium.core.WebDriverEncapsulation;
import com.github.arachnidium.core.settings.CapabilitySettings;
import com.github.arachnidium.core.settings.WebDriverSettings;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.logging.Log;
import com.github.arachnidium.util.proxy.EnhancedProxyFactory;
import com.github.arachnidium.util.reflect.executable.ExecutableUtil;

/**
 * Utility class that contains methods which create {@link Application}
 * instances
 */
public abstract class ApplicationFactory {
	protected Configuration config; //By this configuration app will be launched
	protected ESupportedDrivers supportedDriver; //desired WebDriver
	protected final Object[] paramValues;
	
	
	private static ESupportedDrivers extractSupportedDriver(Configuration configuration){
		return configuration.getSection(WebDriverSettings.class).getSupoortedWebDriver();
	}
	
	private static Object[] extractRequiredParameters(Configuration configuration){
		ESupportedDrivers supportedDriver = extractSupportedDriver(configuration);
		
		Capabilities caps = configuration.getSection(CapabilitySettings.class);
		URL remoteUrl = configuration.getSection(WebDriverSettings.class).getRemoteAddress();
		
		if (caps == null){
			caps = supportedDriver.getDefaultCapabilities();
		}

		if (caps.asMap().size() == 0){
			caps = supportedDriver.getDefaultCapabilities();
		}
		
		DesiredCapabilities dc = new DesiredCapabilities();
		DesiredCapabilities capabilities = dc.merge(supportedDriver.getDefaultCapabilities()).merge(
					caps);	
		
		if (supportedDriver.startsRemotely() & remoteUrl != null)
			return new Object[] { remoteUrl, capabilities };
		else {
			if (remoteUrl == null & supportedDriver.requiresRemoteURL())
				throw new IllegalArgumentException(
						"Defined driver '"
								+ supportedDriver.toString()
								+ "' requires remote address (URL)! Please, define it in settings.json "
								+ "or use suitable constructor");
			if (remoteUrl != null)
				Log.message("Remote address " + String.valueOf(remoteUrl)
						+ " has been ignored");
			return new Object[] { capabilities };
		}
	}
	
	/**
	 * If factory instantiated this way 
	 * the app will be started using the given {@link Configuration}
	 */
	protected ApplicationFactory(Configuration configuration){
		this(extractSupportedDriver(configuration), extractRequiredParameters(configuration));
		config = configuration;
	}
	
	/**
	 * If factory instantiated this way 
	 * the app will be started using {@link Configuration#byDefault}
	 */
	protected ApplicationFactory(){
		this(Configuration.byDefault);
	}
	
	/**
	 * If factory instantiated this way 
	 * the app will be started using desired
	 * {@link WebDriver} description
	 * and its default {@link Capabilities} 
	 */
	protected ApplicationFactory(ESupportedDrivers supportedDriver){
		this(supportedDriver, supportedDriver.getDefaultCapabilities(), null);
	}
	
	/**
	 * If factory instantiated this way 
	 * the app will be started using desired
	 * {@link WebDriver} description
	 * and given {@link Capabilities} 
	 */
	protected ApplicationFactory(ESupportedDrivers supportedDriver, 
			Capabilities capabilities){
		this(supportedDriver, capabilities, null);
	}	
	
	/**
	 * If factory instantiated this way 
	 * the app will be started using desired
	 * {@link WebDriver} description, given {@link Capabilities} 
	 * and URL to the desired remote host
	 */
	protected ApplicationFactory(ESupportedDrivers supportedDriver, 
			Capabilities capabilities, URL remoteUrl){
		this(supportedDriver, new Object[]{capabilities, remoteUrl});
	}
	
	protected ApplicationFactory(ESupportedDrivers supportedDriver, Object[] params){
		this.supportedDriver = supportedDriver;
		config = null;
		this.paramValues = params;
	}
	
	protected interface WebDriverDesignationChecker {
		void checkGivenDriver(ESupportedDrivers givenWebDriverDesignation)
				throws IllegalArgumentException;
	}

	protected <T extends Application<?, ?>> T launch(
			Class<? extends Manager<?,?>> handleManagerClass, Class<T> appClass,
			WebDriverDesignationChecker objectWhichChecksWebDriver) {
		Handle h = null;
		try {
			objectWhichChecksWebDriver.checkGivenDriver(supportedDriver);
			prelaunch();
			h = getTheFirstHandle(handleManagerClass);
			if (config != null){
				h.driverEncapsulation.resetAccordingTo(config);
			}
			
			Object[] params = new Object[] { h };
			Constructor<?> c = ExecutableUtil.getRelevantConstructor(appClass, params);
			
			if (c == null){
				throw new RuntimeException(new NoSuchMethodException("There is no cunstructor which matches to " + Arrays.asList(params).toString() + 
						". The target class is " + appClass.getName()));
			}
			
			T result = EnhancedProxyFactory.getProxy(appClass,
					c.getParameterTypes(),
					new Object[] { h }, new ApplicationInterceptor() {
					});
			DecompositionUtil.populateFieldsWhichAreDecomposable(result);
			return result;
		} catch (Exception e) {
			if (h != null) {
				h.driverEncapsulation.destroy();
			}
			throw new RuntimeException(e);
		}	
	}	
	
	/**
	 * The starting of the desired application by given parameters
	 * 
	 * @param appClass is the desired app representation
	 * @return an instance of the given appClass
	 */
	public abstract <T extends Application<?, ?>> T launch(Class<T> appClass);

	private void prelaunch() {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.merge(supportedDriver.getDefaultCapabilities());
		
		Arrays.asList(paramValues).forEach(param -> {
			if (Capabilities.class.isAssignableFrom(param.getClass()))
				dc.merge((Capabilities) param);
		});
		
		supportedDriver.launchRemoteServerLocallyIfWasDefined();
		if (config == null){
			supportedDriver.setSystemProperty(Configuration.byDefault, dc);
			return;
		}
		supportedDriver.setSystemProperty(config, dc);
	}
	
	Handle getTheFirstHandle(
			Class<? extends Manager<?,?>> handleManagerClass) {
		try {
			WebDriverEncapsulation wdeInstance = new WebDriverEncapsulation(supportedDriver, paramValues);
			
			Constructor<?> c = handleManagerClass
					.getConstructor(new Class<?>[] { WebDriverEncapsulation.class });
			Manager<?,?> m = (Manager<?,?>) c
					.newInstance(new Object[] { wdeInstance });
	
			return m.getHandle(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
