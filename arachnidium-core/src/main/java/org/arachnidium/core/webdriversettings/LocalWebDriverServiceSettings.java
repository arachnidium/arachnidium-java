package org.arachnidium.core.webdriversettings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.configuration.interfaces.IHasPathToFile;


/**
 * @author s.tihomirov getters for local webdriver service configuration data
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

	@Override
	public String getFolder() {
		return (String) getSetting(folderSettingName);
	}

	@Override
	public String getFile() {
		return (String) getSetting(fileSettingName);
	}

	@Override
	public Object getSetting(String name) {
		return getSettingValue(localWebdriverServiceGroup, name);
	}

}
