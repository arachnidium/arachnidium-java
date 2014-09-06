package org.arachnidium.util.logging;

import java.util.logging.Level;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

/**
 * Settings of logger. It uses java.utils.logging It is used
 *         only with default configuration
 */
class LoggingHelper extends AbstractConfigurationAccessHelper {

	private final String levelSetting = "Level";
	// Logging group
	private final String loggingGroup = "Log";

	public LoggingHelper(Configuration configuration) {
		super(configuration);
	}

	public Level getLevel() {
		String levelName = getSetting(levelSetting);
		if (levelName != null)
			return Level.parse(levelName.toUpperCase());
		else
			return null;
	}

	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(loggingGroup, name);
	}

}
