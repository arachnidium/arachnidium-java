package com.github.arachnidium.util.configuration;

import java.util.HashMap;

/**
 * Subclasses of this should make access to
 *         {@link Configuration} data easier
 */
public abstract class AbstractConfigurationAccessHelper{

	private final Configuration configuration;

	/**
	 * This constructor should present in subclass
	 * 
	 * @param configuration to get data from
	 * @see Configuration
	 */
	public AbstractConfigurationAccessHelper(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	protected HashMap<String, Object> getGroup(String groupName) {
		return configuration.getSettingGroup(groupName);
	}
	
	public abstract <T extends Object> T getSetting(String name);

	protected <T extends Object> T getSettingValue(String groupName, String settingName) {
		return configuration.getSettingValue(groupName, settingName);
	}
}
