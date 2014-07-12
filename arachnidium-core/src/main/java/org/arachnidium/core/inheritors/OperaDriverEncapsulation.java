package org.arachnidium.core.inheritors;

import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.webdriversettings.supported.ESupportedDrivers;
import org.arachnidium.util.configuration.Configuration;

import com.opera.core.systems.OperaProfile;
import com.opera.core.systems.OperaSettings;

public final class OperaDriverEncapsulation extends WebDriverEncapsulation {

	public OperaDriverEncapsulation(OperaProfile profile) {
		super();
		createWebDriver(ESupportedDrivers.OPERA.getUsingWebDriverClass(),
				new Class[] { OperaProfile.class }, new Object[] { profile });
	}

	public OperaDriverEncapsulation(OperaProfile profile, Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(ESupportedDrivers.OPERA.getUsingWebDriverClass(),
				new Class[] { OperaProfile.class }, new Object[] { profile });
	}

	public OperaDriverEncapsulation(OperaSettings settings) {
		super();
		createWebDriver(ESupportedDrivers.OPERA.getUsingWebDriverClass(),
				new Class[] { OperaSettings.class }, new Object[] { settings });
	}

	public OperaDriverEncapsulation(OperaSettings settings, Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(ESupportedDrivers.OPERA.getUsingWebDriverClass(),
				new Class[] { OperaSettings.class }, new Object[] { settings });
	}

}
