package org.arachnidium.core.inheritors;

import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.webdriversettings.supported.ESupportedDrivers;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;


public final class FirefoxDriverEncapsulation extends WebDriverEncapsulation {

	public FirefoxDriverEncapsulation(FirefoxProfile profile) {
		super();
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(),
				new Class<?>[] { FirefoxProfile.class },
				new Object[] { profile });
	}

	public FirefoxDriverEncapsulation(FirefoxProfile profile,
			Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(),
				new Class<?>[] { FirefoxProfile.class },
				new Object[] { profile });
	}

	public FirefoxDriverEncapsulation(Capabilities desiredCapabilities,
			Capabilities requiredCapabilities) {
		super();
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(),
				new Class<?>[] { Capabilities.class, Capabilities.class },
				new Object[] { desiredCapabilities, requiredCapabilities });
	}

	public FirefoxDriverEncapsulation(Capabilities desiredCapabilities,
			Capabilities requiredCapabilities, Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(),
				new Class<?>[] { Capabilities.class, Capabilities.class },
				new Object[] { desiredCapabilities, requiredCapabilities });
	}

	public FirefoxDriverEncapsulation(FirefoxBinary binary,
			FirefoxProfile profile) {
		super();
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(),
				new Class<?>[] { FirefoxBinary.class, FirefoxProfile.class },
				new Object[] { binary, profile });
	}

	public FirefoxDriverEncapsulation(FirefoxBinary binary,
			FirefoxProfile profile, Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(),
				new Class<?>[] { FirefoxBinary.class, FirefoxProfile.class },
				new Object[] { binary, profile });
	}

	public FirefoxDriverEncapsulation(FirefoxBinary binary,
			FirefoxProfile profile, Capabilities capabilities,
			Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(),
				new Class<?>[] { FirefoxBinary.class, FirefoxProfile.class,
						Capabilities.class }, new Object[] { binary, profile,
						capabilities });
	}

	public FirefoxDriverEncapsulation(FirefoxBinary binary,
			FirefoxProfile profile, Capabilities desiredCapabilities,
			Capabilities requiredCapabilities, Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(),
				new Class<?>[] { FirefoxBinary.class, FirefoxProfile.class,
						Capabilities.class, Capabilities.class }, new Object[] {
						binary, profile, desiredCapabilities,
						requiredCapabilities });
	}

	public FirefoxDriverEncapsulation(FirefoxBinary binary,
			FirefoxProfile profile, Capabilities desiredCapabilities,
			Capabilities requiredCapabilities) {
		super();
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(),
				new Class<?>[] { FirefoxBinary.class, FirefoxProfile.class,
						Capabilities.class, Capabilities.class }, new Object[] {
						binary, profile, desiredCapabilities,
						requiredCapabilities });
	}

	public FirefoxDriverEncapsulation(FirefoxBinary binary,
			FirefoxProfile profile, Capabilities capabilities) {
		super();
		createWebDriver(ESupportedDrivers.FIREFOX.getUsingWebDriverClass(),
				new Class<?>[] { FirefoxBinary.class, FirefoxProfile.class,
						Capabilities.class }, new Object[] { binary, profile,
						capabilities });
	}
}
