package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.Configuration;

/**
 * @author s.tihomirov settings of chromedriver.exe
 */
public class ChromeDriverServerBin extends LocalWebDriverServiceSettings {
	private static final String chromeDriverGroup = "ChromeDriver";

	public ChromeDriverServerBin(Configuration configuration) {
		super(configuration, chromeDriverGroup);
	}

}
