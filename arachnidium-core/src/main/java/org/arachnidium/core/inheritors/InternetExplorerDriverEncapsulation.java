package org.arachnidium.core.inheritors;

import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.webdriversettings.supported.ESupportedDrivers;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public final class InternetExplorerDriverEncapsulation extends
WebDriverEncapsulation {

	public InternetExplorerDriverEncapsulation(int port) {
		super();
		ESupportedDrivers.INTERNETEXPLORER
		.setSystemProperty(Configuration.byDefault,
				DesiredCapabilities.internetExplorer());
		createWebDriver(
				ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(),
				new Class[] { int.class }, new Object[] { port });
	}

	public InternetExplorerDriverEncapsulation(int port, Configuration config) {
		super();
		ESupportedDrivers.INTERNETEXPLORER.setSystemProperty(config,
				DesiredCapabilities.internetExplorer());
		this.configuration = config;
		createWebDriver(
				ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(),
				new Class[] { int.class }, new Object[] { port });
	}

	public InternetExplorerDriverEncapsulation(
			InternetExplorerDriverService service) {
		super();
		createWebDriver(
				ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(),
				new Class[] { InternetExplorerDriverService.class },
				new Object[] { service });
	}

	public InternetExplorerDriverEncapsulation(
			InternetExplorerDriverService service, Capabilities capabilities) {
		super();
		createWebDriver(
				ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(),
				new Class[] { InternetExplorerDriverService.class,
					Capabilities.class }, new Object[] { service,
					capabilities });
	}

	public InternetExplorerDriverEncapsulation(
			InternetExplorerDriverService service, Capabilities capabilities,
			Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(
				ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(),
				new Class[] { InternetExplorerDriverService.class,
					Capabilities.class }, new Object[] { service,
					capabilities });
	}

	public InternetExplorerDriverEncapsulation(
			InternetExplorerDriverService service, Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(
				ESupportedDrivers.INTERNETEXPLORER.getUsingWebDriverClass(),
				new Class[] { InternetExplorerDriverService.class },
				new Object[] { service });
	}
}
