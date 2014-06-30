package org.arachnidium.util.configuration.commonhelpers;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.configuration.webdriver.ESupportedDrivers;


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

	@Override
	public Object getSetting(String name) {
		return getSettingValue(webDriverGroup, name);
	}

	public String getRemoteAddress() {
		return (String) getSetting(remoteAddress);
	}

	public ESupportedDrivers getSupoortedWebDriver() {
		String name = (String) getSetting(webDriverName);
		if (name != null) {
			return ESupportedDrivers.parse(name);
		} else {
			return null;
		}
	}

}
