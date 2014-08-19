package org.arachnidium.model.mobile;

import java.net.MalformedURLException;
import java.net.URL;

import org.arachnidium.core.ScreenManager;
import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.settings.CapabilitySettings;
import org.arachnidium.core.settings.WebDriverSettings;
import org.arachnidium.core.settings.supported.ESupportedDrivers;
import org.arachnidium.model.common.Application;
import org.arachnidium.model.common.DefaultApplicationFactory;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.Capabilities;

public final class MobileFactory extends DefaultApplicationFactory {

	/**
	 * Common method that creates an instance of a mobile application using
	 * default configuration It is important: URL to remote server and
	 * capabilities should be defined in default configuration
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass) {
		return getApplication(appClass, Configuration.byDefault);
	}

	/**
	 * Common method that creates an instance of a mobile application using
	 * defined address of remote server and capabilities.
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, Capabilities capabilities, URL remoteAddress) {
		return getApplication(ScreenManager.class, appClass,
				ESupportedDrivers.MOBILE, capabilities, remoteAddress,
				new MobileApplicationInterceptor());
	}

	/**
	 * Common method that creates an instance of a mobile application using
	 * defined configuration It is important: URL to remote server and
	 * capabilities should be defined in configuration
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, Configuration configuration) {
		try {
			WebDriverEncapsulation wdEncapsulation = new WebDriverEncapsulation(
					ESupportedDrivers.MOBILE,
					configuration.getSection(CapabilitySettings.class),
					new URL(configuration.getSection(WebDriverSettings.class)
							.getRemoteAddress()));
			wdEncapsulation.resetAccordingTo(configuration);
			T result = getApplication(ScreenManager.class, appClass,
					wdEncapsulation, new MobileApplicationInterceptor());
			return result;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Common method that creates an instance of a mobile application using
	 * defined address of remote server. It is important: Remote Appium server
	 * should be tuned
	 */
	public static <T extends Application<?, ?>> T getApplication(
			Class<T> appClass, URL remoteAddress) {
		return getApplication(ScreenManager.class, appClass,
				ESupportedDrivers.MOBILE, remoteAddress,
				new MobileApplicationInterceptor());
	}

	private MobileFactory() {
		super();
	}

}
