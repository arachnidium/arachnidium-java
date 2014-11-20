package com.github.arachnidium.model.browser;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.WindowManager;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.core.settings.supported.ExtendedCapabilityType;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.common.ApplicationFactory;
import com.github.arachnidium.util.configuration.Configuration;

public final class WebFactory extends ApplicationFactory {
	private static WebDriverDesignationChecker objectWhichChecksWebDriver = givenWebDriverDesignation -> {
			if (!givenWebDriverDesignation.isForBrowser()){
				throw new IllegalArgumentException(givenWebDriverDesignation.toString() + 
						" is not for browser launching!");
			}
		};
	@SuppressWarnings("unchecked")
	private static Map<String, Object> setInitURLToCapabilityMap(Capabilities capabilities, String initURL){
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll((Map<String, Object>) capabilities.asMap());
		map.put(ExtendedCapabilityType.BROWSER_INITIAL_URL, initURL);
		return map;
	}


	
	/**
	 * If factory instantiated this way 
	 * browser will be started using {@link Configuration#byDefault}
	 */
	public WebFactory(){
		super();
	}

	/**
	 * If factory instantiated this way 
	 * browser will be started using the given {@link Configuration}
	 */
	public WebFactory(Configuration configuration){
		super(configuration);
	}



	/**
	 * If factory instantiated this way 
	 * browser will be started using desired
	 * {@link WebDriver} description
	 * and its default {@link Capabilities} 
	 */
	public WebFactory(ESupportedDrivers supportedDriver){
		super(supportedDriver);
	}



	/**
	 * If factory instantiated this way 
	 * browser will be started using desired
	 * {@link WebDriver} description
	 * and given {@link Capabilities} 
	 */
	public WebFactory(ESupportedDrivers supportedDriver, 
			Capabilities capabilities){
		super(supportedDriver, capabilities);
	}



	/**
	 * If factory instantiated this way 
	 * browser will be started using desired
	 * {@link WebDriver} description, given {@link Capabilities} 
	 * and URL to the desired remote host
	 */
	public WebFactory(ESupportedDrivers supportedDriver, 
			Capabilities capabilities, URL remoteUrl){
		super(supportedDriver, capabilities, remoteUrl);
	}



	@Override
	public <T extends Application<?, ?>> T launch(Class<T> appClass) {
		return launch(WindowManager.class, appClass, new BrowserApplicationInterceptor(), BrowserInteractiveInterceptor.class, 
				objectWhichChecksWebDriver);
	}
	
	/**
	 * The launching of the browser app using URL which has to be loaded
	 * 
	 * @param appClass is the desired app representation
	 * @param desiredUrl is the URL which has to be loaded
	 * @return an instance of the given appClass
	 */
	public <T extends Application<?, ?>> T launch(Class<T> appClass, String desiredUrl) {
		capabilities = new DesiredCapabilities(setInitURLToCapabilityMap(capabilities, desiredUrl));
		return launch(WindowManager.class, appClass, new BrowserApplicationInterceptor(), BrowserInteractiveInterceptor.class,
				objectWhichChecksWebDriver);
	}
	
	
}
