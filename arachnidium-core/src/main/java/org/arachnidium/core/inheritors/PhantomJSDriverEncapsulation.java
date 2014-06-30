package org.arachnidium.core.inheritors;

import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.webdriversettings.supported.ESupportedDrivers;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.service.DriverService;

public final class PhantomJSDriverEncapsulation extends WebDriverEncapsulation {
	public PhantomJSDriverEncapsulation(DriverService service,
			Capabilities desiredCapabilities) {
		super();
		createWebDriver(ESupportedDrivers.PHANTOMJS.getUsingWebDriverClass(),
				new Class<?>[] { DriverService.class, Capabilities.class },
				new Object[] { service, desiredCapabilities });
	}

	public PhantomJSDriverEncapsulation(DriverService service,
			Capabilities desiredCapabilities, Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(ESupportedDrivers.PHANTOMJS.getUsingWebDriverClass(),
				new Class<?>[] { DriverService.class, Capabilities.class },
				new Object[] { service, desiredCapabilities });
	}
}
