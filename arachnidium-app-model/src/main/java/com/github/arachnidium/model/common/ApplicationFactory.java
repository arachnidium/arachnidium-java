package com.github.arachnidium.model.common;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.Manager;
import com.github.arachnidium.core.settings.CapabilitySettings;
import com.github.arachnidium.core.settings.WebDriverSettings;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.proxy.EnhancedProxyFactory;

/**
 * Utility class that contains methods which create {@link Application}
 * instances
 */
public abstract class ApplicationFactory {
	protected Configuration config; //By this configuration app will be launched
	protected ESupportedDrivers supportedDriver; //desired WebDriver
	protected Capabilities capabilities;//desired capabilities
	protected URL remoteUrl; //URL to the desired remotehost 
	
	/**
	 * If factory instantiated this way 
	 * the app will be started using the given {@link Configuration}
	 */
	protected ApplicationFactory(Configuration configuration){
		super();
		config = configuration;
		WebDriverSettings wdSettings = configuration.getSection(WebDriverSettings.class);
		supportedDriver = configuration.getSection(WebDriverSettings.class).getSupoortedWebDriver();
		Capabilities caps = configuration.getSection(CapabilitySettings.class);
		remoteUrl = wdSettings.getRemoteAddress();
		
		if (caps == null){
			capabilities = supportedDriver.getDefaultCapabilities();
			return;
		}

		if (caps.asMap().size() == 0){
			capabilities = supportedDriver.getDefaultCapabilities();
			return;
		}

		DesiredCapabilities dc = new DesiredCapabilities();
		capabilities = dc.merge(supportedDriver.getDefaultCapabilities()).merge(
					caps);		
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
		this(supportedDriver, null, null);
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
		this.supportedDriver = supportedDriver;
		config = null;
		this.capabilities = capabilities;
		this.remoteUrl = remoteUrl;
	}	
	
	protected interface WebDriverDesignationChecker {
		void checkGivenDriver(ESupportedDrivers givenWebDriverDesignation)
				throws IllegalArgumentException;
	}
	
	private Class<?>[] getInitParamClasses(){
		if (remoteUrl != null){
			return new Class[]{ESupportedDrivers.class, Capabilities.class, URL.class};
		}
		
		return new Class[]{ESupportedDrivers.class, Capabilities.class};
	}
	
	private Object[] getInitParamValues(){
		if (remoteUrl != null){
			return new Object[]{supportedDriver, capabilities, remoteUrl};
		}
		
		return new Object[]{supportedDriver, capabilities};
	}	

	protected <T extends Application<?, ?>> T launch(
			Class<? extends Manager<?>> handleManagerClass, Class<T> appClass,
			ApplicationInterceptor<?, ?, ?, ?, ?> mi, Class<? extends InteractiveInterceptor<?>> interactiveInterceptor,
			WebDriverDesignationChecker objectWhichChecksWebDriver) {
		Handle h = null;
		try {
			h = ModelSupportUtil.getTheFirstHandle(handleManagerClass, getInitParamClasses(),
					getInitParamValues());
			if (config != null){
				h.getDriverEncapsulation().resetAccordingTo(config);
			}
			T result = EnhancedProxyFactory.getProxy(appClass,
					ModelSupportUtil.getParameterClasses(new Object[] { h }, appClass),
					new Object[] { h }, mi);
			result.usedInteractiveInterceptor = interactiveInterceptor;
			return result;
		} catch (Exception e) {
			if (h != null) {
				h.getDriverEncapsulation().destroy();
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
}
