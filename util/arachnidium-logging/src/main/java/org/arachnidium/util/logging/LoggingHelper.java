package org.arachnidium.util.logging;

import java.util.logging.Level;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

/**
 * Stores minimal {@link Level} of messages that are shown by console
 * and minimal {@link Level} of a user message
 * implicitly waiting.
 * 
 * @see Configuration
 * 
 * Specification:
 * 
 * ...
 * "Log":
  {
      "Level":{
          "type":"STRING",
          "value":"some level designation" @see Level           
      }      
  }
  ...
 */
class LoggingHelper extends AbstractConfigurationAccessHelper {

	private final String levelSetting = "Level";
	// Logging group
	private final String loggingGroup = "Log";

	public LoggingHelper(Configuration configuration) {
		super(configuration);
	}

	/**
	 * @return Specified log {@link Level}
	 */
	public Level getLevel() {
		String levelName = getSetting(levelSetting);
		if (levelName != null)
			return Level.parse(levelName.toUpperCase());
		else
			return null;
	}

	/**
	 * @see org.arachnidium.util.configuration.AbstractConfigurationAccessHelper#getSetting(java.lang.String)
	 */
	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(loggingGroup, name);
	}

}
