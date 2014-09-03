package org.arachnidium.model.browser;

import java.net.URL;

import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.WindowManager;
import org.arachnidium.core.settings.supported.ESupportedDrivers;
import org.arachnidium.model.common.Application;
import org.arachnidium.model.common.DefaultApplicationFactory;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.Capabilities;

public final class WebFactory extends DefaultApplicationFactory {

	/**
	 * Common method that creates an instance of a browser application with
	 * defined configuration. Application is loaded using it's URL
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, Configuration config, String urlToBeLoaded) {
		return load(
				getApplication(WindowManager.class, appClass, config,
						new BrowserApplicationInterceptor()), urlToBeLoaded);
	}

	/**
	 * Common method that creates an instance of a browser application with
	 * defined webdriver and its capabilities. Application is loaded using it's
	 * URL
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
	 * Common method that creates an instance of a browser application with
	 * defined webdriver, capabilities and URL to remote server. Application is
	 * loaded using it's URL
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
	 * Common method that creates an instance of a browser application with
	 * defined webdriver. Application is loaded using it's URL
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, ESupportedDrivers supportedDriver,
			String urlToBeLoaded) {
		return load(
				getApplication(WindowManager.class, appClass, supportedDriver,
						new BrowserApplicationInterceptor()), urlToBeLoaded);
	}

	/**
	 * Common method that creates an instance of a browser application with
	 * defined webdriver and URL to remote server. Application is loaded using
	 * it's URL
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
	 * Common method that creates an instance of a browser application with
	 * default configuration. Application is loaded using it's URL
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, String urlToBeLoaded) {
		return load(
				getApplication(WindowManager.class, appClass,
						new BrowserApplicationInterceptor()), urlToBeLoaded);
	}

	/**
	 * Common method that creates an instance of a browser application with
	 * externally instantiated {@link WebDriverEncapsulation}. Application is
	 * loaded using it's URL
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
		instance.getWebDriverEncapsulation().getTo(urlToBeLoaded);
		return instance;
	}

	private WebFactory() {
		super();
	}

}
