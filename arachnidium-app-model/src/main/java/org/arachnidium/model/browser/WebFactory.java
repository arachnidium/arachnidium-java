package org.arachnidium.model.browser;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.model.common.DefaultApplicationFactory;
import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.WindowManager;
import org.arachnidium.core.webdriversettings.supported.ESupportedDrivers;

public final class WebFactory extends DefaultApplicationFactory {

	private WebFactory(){
		super();
	}

	private static <T extends BrowserApplication> T load(T instance, String urlToBeLoaded){
		getWebDriverEncapsulation(instance).getTo(urlToBeLoaded);
		return instance;
	}
	
	/**
	 * Common method that creates an instance of a browser application
	 * with default configuration
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass){
		return getApplication(WindowManager.class, appClass);
	}
	
	/**
	 * Common method that creates an instance of a browser application
	 * with default configuration. Application is loaded using it's URL
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass, String urlToBeLoaded){
		return load(getApplication(WindowManager.class, appClass), urlToBeLoaded);
	}

	/** Common method that creates an instance of a browser application 
	 * with defined configuration
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			Configuration config) {
		return getApplication(WindowManager.class, appClass, config);
	}
	
	/** Common method that creates an instance of a browser application 
	 * with defined configuration. Application is loaded using it's URL
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			Configuration config, String urlToBeLoaded) {
		return load(getApplication(WindowManager.class, appClass, config), urlToBeLoaded);
	}

	/** Common method that creates an instance of a browser application 
	 * with defined webdriver
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			ESupportedDrivers supportedDriver){
		return getApplication(WindowManager.class, appClass, supportedDriver);
	}
	
	/** Common method that creates an instance of a browser application 
	 * with defined webdriver. Application is loaded using it's URL
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			ESupportedDrivers supportedDriver, String urlToBeLoaded){
		return load(getApplication(WindowManager.class, appClass, supportedDriver), urlToBeLoaded);
	}

	/** Common method that creates an instance of a browser application
	 * with defined webdriver and its capabilities
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities) {
		return getApplication(WindowManager.class, appClass, supportedDriver, capabilities);
	}
	
	/** Common method that creates an instance of a browser application
	 * with defined webdriver and its capabilities. Application is loaded using it's URL
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities, String urlToBeLoaded) {
		return load(getApplication(WindowManager.class, appClass, supportedDriver, capabilities), urlToBeLoaded);
	}

	/** Common method that creates an instance of a browser application
	 * with defined webdriver, capabilities and URL to remote server
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			URL remoteAddress) {
		return getApplication(WindowManager.class, appClass, supportedDriver, capabilities, remoteAddress);
	}
	
	/** Common method that creates an instance of a browser application
	 * with defined webdriver, capabilities and URL to remote server. Application is loaded using it's URL
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			ESupportedDrivers supportedDriver, Capabilities capabilities,
			URL remoteAddress, String urlToBeLoaded) {
		return load(getApplication(WindowManager.class, appClass, supportedDriver, capabilities, remoteAddress), urlToBeLoaded);
	}

	/** Common method that creates an instance of a browser application
	 * with defined webdriver and URL to remote server
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			ESupportedDrivers supportedDriver, 
			URL remoteAddress) {
		return getApplication(WindowManager.class, appClass, supportedDriver, remoteAddress);
	}
	
	/** Common method that creates an instance of a browser application
	 * with defined webdriver and URL to remote server. Application is loaded using it's URL
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			ESupportedDrivers supportedDriver, 
			URL remoteAddress, String urlToBeLoaded) {
		return load(getApplication(WindowManager.class, appClass, supportedDriver, remoteAddress), urlToBeLoaded);
	}

	/** Common method that creates an instance of a browser application
	 * with externally instantiated {@link WebDriverEncapsulation}
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			WebDriverEncapsulation wdEncapsulation) {
		return getApplication(WindowManager.class, appClass, wdEncapsulation);
	}
	
	/** Common method that creates an instance of a browser application
	 * with externally instantiated {@link WebDriverEncapsulation}. Application is loaded using it's URL
	 */
	public static <T extends BrowserApplication> T getApplication(Class<T> appClass,
			WebDriverEncapsulation wdEncapsulation, String urlToBeLoaded) {
		return load(getApplication(WindowManager.class, appClass, wdEncapsulation), urlToBeLoaded);
	}
	
}
