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
abstract class LocalWebDriverServiceSettings extends AbstractConfigurationAccessHelper
implements IHasPathToFile {

	protected LocalWebDriverServiceSettings(Configuration configuration,
			String desiredSettingGroup) {
		super(configuration, desiredSettingGroup);
	}

	/**
	 * @return file name of {@link DriverService} binary file
	 * specified in {@link Configuration}
	 */
	@Override
	@Setting(setting = "file")
	public String getFile() {
		return getSetting();
	}

	/**
	 * @return path to folder with {@link DriverService} binary file
	 * specified in {@link Configuration}
	 */
	@Override
	@Setting(setting = "folder")
	public String getFolder() {
		return getSetting();
	}

}
