package org.arachnidium.util.configuration;

import java.util.HashMap;

import org.arachnidium.util.configuration.interfaces.IGroupedSetting;


/**
 * @author s.tihomirov Inheritors of this class should make access to
 *         configuration data easier
 */
public abstract class AbstractConfigurationAccessHelper implements
		IGroupedSetting {

	private final Configuration configuration;

	public AbstractConfigurationAccessHelper(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	protected Object getSettingValue(String groupName, String settingName) {
		return configuration.getSettingValue(groupName, settingName);
	}

	protected HashMap<String, Object> getGroup(String groupName) {
		return configuration.getSettingGroup(groupName);
	}

	@Override
	public abstract Object getSetting(String name);
}
