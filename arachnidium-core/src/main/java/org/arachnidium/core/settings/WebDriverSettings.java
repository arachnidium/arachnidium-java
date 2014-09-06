package org.arachnidium.core.settings;

import org.arachnidium.core.settings.supported.ESupportedDrivers;
import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

/**
 * @author s.tihomirov Parameters of a webdriver that will be created
 */
public class WebDriverSettings extends AbstractConfigurationAccessHelper {

	private final String remoteAddress = "remoteAdress";
	private final String webDriverName = "driverName";
	private final String webDriverGroup = "webdriver";

	public WebDriverSettings(Configuration configuration) {
		super(configuration);
	}

	public String getRemoteAddress() {
		return getSetting(remoteAddress);
	}

	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(webDriverGroup, name);
	}

	public ESupportedDrivers getSupoortedWebDriver() {
		String name = getSetting(webDriverName);
		if (name != null)
			return ESupportedDrivers.parse(name);
		else
			return null;
	}

}
