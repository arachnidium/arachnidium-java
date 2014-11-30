package com.github.arachnidium.model.mobile;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import com.github.arachnidium.core.ScreenManager;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.common.ApplicationFactory;
import com.github.arachnidium.util.configuration.Configuration;

public final class MobileFactory extends ApplicationFactory {
	private static WebDriverDesignationChecker objectWhichChecksWebDriver = givenWebDriverDesignation -> {
		if (!givenWebDriverDesignation.isForMobileApp()) {
			throw new IllegalArgumentException(
					givenWebDriverDesignation.toString()
							+ " is not for mobile " + "app launching!");
		}
	};

	/**
	 * If factory instantiated this way mobile the app will be started using
	 * {@link Configuration#byDefault}
	 */
	public MobileFactory() {
		super();
	}

	/**
	 * If factory instantiated this way the mobile app will be started using the
	 * given {@link Configuration}
	 */
	public MobileFactory(Configuration configuration) {
		super(configuration);
	}

	/**
	 * If factory instantiated this way the mobile app will be started using
	 * desired {@link WebDriver} description, given {@link Capabilities} and URL
	 * to the desired remote host
	 */
	public MobileFactory(ESupportedDrivers supportedDriver,
			Capabilities capabilities, URL remoteUrl) {
		super(supportedDriver, capabilities, remoteUrl);
	}

	@Override
	public <T extends Application<?, ?>> T launch(Class<T> appClass) {
		return launch(ScreenManager.class, appClass, objectWhichChecksWebDriver);
	}
}
