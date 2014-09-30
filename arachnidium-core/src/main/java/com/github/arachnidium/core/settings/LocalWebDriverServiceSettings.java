package com.github.arachnidium.core.settings;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.interfaces.IHasPathToFile;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.service.DriverService;

/**
 * Stores path to folder and file name
 * of chromedriver*, IEDriverServer.exe, phantomjs*
 * 
 * @see Configuration
 * @see DriverService
 * @see ChromeDriverServerBin
 * @see InternetExplorerDriverService
 * @see PhantomJSDriverService
 * 
 */
class LocalWebDriverServiceSettings extends AbstractConfigurationAccessHelper
implements IHasPathToFile {

	protected final String localWebdriverServiceGroup;
	private final String fileSettingName = "file";
	// spicified settings for *Driver.exe
	private final String folderSettingName = "folder";

	public LocalWebDriverServiceSettings(Configuration configuration,
			String groupName) {
		super(configuration);
		localWebdriverServiceGroup = groupName;
	}

	/**
	 * @return file name of {@link DriverService} binary file
	 * specified in {@link Configuration}
	 */
	@Override
	public String getFile() {
		return getSetting(fileSettingName);
	}

	/**
	 * @return path to folder with {@link DriverService} binary file
	 * specified in {@link Configuration}
	 */
	@Override
	public String getFolder() {
		return getSetting(folderSettingName);
	}

	/**
	 * @see com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper#getSetting(java.lang.String)
	 */
	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(localWebdriverServiceGroup, name);
	}

}
