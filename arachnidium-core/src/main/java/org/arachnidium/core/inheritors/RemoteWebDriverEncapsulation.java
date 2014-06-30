package org.arachnidium.core.inheritors;

import java.net.URL;

import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.webdriversettings.supported.ESupportedDrivers;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;

public final class RemoteWebDriverEncapsulation extends WebDriverEncapsulation {

	public RemoteWebDriverEncapsulation(Capabilities capabilities,
			CommandExecutor executor) {
		super();
		prelaunch(ESupportedDrivers.REMOTE, this.configuration, capabilities);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(),
				new Class[] { CommandExecutor.class, Capabilities.class },
				new Object[] { executor, capabilities });
	}

	public RemoteWebDriverEncapsulation(Capabilities capabilities,
			CommandExecutor executor, Configuration config) {
		super();
		this.configuration = config;
		prelaunch(ESupportedDrivers.REMOTE, this.configuration, capabilities);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(),
				new Class[] { CommandExecutor.class, Capabilities.class },
				new Object[] { executor, capabilities });
	}

	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities,
			Capabilities requiredCapabilities, CommandExecutor executor) {
		super();
		prelaunch(ESupportedDrivers.REMOTE, this.configuration, desiredCapabilities);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(),
				new Class[] { CommandExecutor.class, Capabilities.class,
						Capabilities.class }, new Object[] { executor,
						desiredCapabilities, requiredCapabilities });
	}

	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities,
			Capabilities requiredCapabilities, CommandExecutor executor,
			Configuration config) {
		super();
		this.configuration = config;
		prelaunch(ESupportedDrivers.REMOTE, this.configuration, desiredCapabilities);
		createWebDriver(ESupportedDrivers.REMOTE.getUsingWebDriverClass(),
				new Class[] { CommandExecutor.class, Capabilities.class,
						Capabilities.class }, new Object[] { executor,
						desiredCapabilities, requiredCapabilities });
	}

	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities,
			Capabilities requiredCapabilities, URL remoteAddress) {
		super();
		createWebDriver(
				ESupportedDrivers.REMOTE.getUsingWebDriverClass(),
				new Class[] { URL.class, Capabilities.class, Capabilities.class },
				new Object[] { remoteAddress, desiredCapabilities,
						requiredCapabilities });
	}

	public RemoteWebDriverEncapsulation(Capabilities desiredCapabilities,
			Capabilities requiredCapabilities, URL remoteAddress,
			Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(
				ESupportedDrivers.REMOTE.getUsingWebDriverClass(),
				new Class[] { URL.class, Capabilities.class, Capabilities.class },
				new Object[] { remoteAddress, desiredCapabilities,
						requiredCapabilities });
	}
}
