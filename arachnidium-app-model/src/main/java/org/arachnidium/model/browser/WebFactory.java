package org.arachnidium.model.browser;

import java.net.URL;

import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.WindowManager;
import org.arachnidium.core.settings.supported.ESupportedDrivers;
import org.arachnidium.model.common.Application;
import org.arachnidium.model.common.DefaultApplicationFactory;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class WebFactory extends DefaultApplicationFactory {

	/**
	 * Common method that creates an instance of any application using defined
	 * configuration. Performs navigation to the required URL 
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, Configuration config, String urlToBeLoaded) {
		return load(
				getApplication(WindowManager.class, appClass, config,
						new BrowserApplicationInterceptor()), urlToBeLoaded);
	}

	/**
	 * Common method that creates an instance of any application using required
	 * {@link RemoteWebDriver} class and its {@link Capabilities}.
	 * The class of {@link RemoteWebDriver} subclass 
	 * is contained by {@link ESupportedDrivers} 
	 * 
	 * Performs navigation to the required URL 
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, ESupportedDrivers supportedDriver,
			Capabilities capabilities, String urlToBeLoaded) {
		return load(
				getApplication(WindowManager.class, appClass, supportedDriver,
						capabilities, new BrowserApplicationInterceptor()),
				urlToBeLoaded);
	}


	/**
	 * Common method that creates an instance of any application using required
	 * {@link RemoteWebDriver} class and its {@link Capabilities} and URL of remote
	 * host where it should be launched.
     * The class of {@link RemoteWebDriver} subclass 
	 * is contained by {@link ESupportedDrivers} 
	 * 
	 * Performs navigation to the required URL 
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, ESupportedDrivers supportedDriver,
			Capabilities capabilities, URL remoteAddress, String urlToBeLoaded) {
		return load(
				getApplication(WindowManager.class, appClass, supportedDriver,
						capabilities, remoteAddress,
						new BrowserApplicationInterceptor()), urlToBeLoaded);
	}

	/**
	 * Common method that creates an instance of any application using required
	 * {@link RemoteWebDriver} class. This class is contained by {@link ESupportedDrivers} 
	 * 
	 * Performs navigation to the required URL 
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, ESupportedDrivers supportedDriver,
			String urlToBeLoaded) {
		return load(
				getApplication(WindowManager.class, appClass, supportedDriver,
						new BrowserApplicationInterceptor()), urlToBeLoaded);
	}

	/**
	 * Common method that creates an instance of any application using required
	 * {@link RemoteWebDriver} class and URL of remote
	 * host where it should be launched.
     * The class of {@link RemoteWebDriver} subclass 
	 * is contained by {@link ESupportedDrivers} 
	 * 
	 * Performs navigation to the required URL 
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, ESupportedDrivers supportedDriver,
			URL remoteAddress, String urlToBeLoaded) {
		return load(
				getApplication(WindowManager.class, appClass, supportedDriver,
						remoteAddress, new BrowserApplicationInterceptor()),
				urlToBeLoaded);
	}

	/**
	 * Common method that creates an instance of any application using default
	 * configuration
	 * 
	 * Performs navigation to the required URL 
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, String urlToBeLoaded) {
		return load(
				getApplication(WindowManager.class, appClass,
						new BrowserApplicationInterceptor()), urlToBeLoaded);
	}

	/**
	 * Common method that creates an instance of any application with externally
	 * instantiated {@link WebDriverEncapsulation}
	 * 
	 * Performs navigation to the required URL 
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, WebDriverEncapsulation wdEncapsulation,
			String urlToBeLoaded) {
		return load(
				getApplication(WindowManager.class, appClass, wdEncapsulation,
						new BrowserApplicationInterceptor()), urlToBeLoaded);
	}

	private static <T extends Application<?, ?>> T load(T instance,
			String urlToBeLoaded) {
		instance.getWebDriverEncapsulation().getWrappedDriver().navigate()
				.to(urlToBeLoaded);
		return instance;
	}

	private WebFactory() {
		super();
	}

}
