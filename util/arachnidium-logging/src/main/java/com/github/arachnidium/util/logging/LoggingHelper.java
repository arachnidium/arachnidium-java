package com.github.arachnidium.util.logging;

import java.util.logging.Level;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;

/**
 * Stores minimal {@link Level} of messages that are shown by console
 * and minimal {@link Level} of a user message
 * implicitly waiting.
 * 
 * Specification:
 * 
 * <p><br/>
 * ...<br/>
 * "Log":<br/>
 * {<br/>
 * &nbsp;&nbsp;"Level":{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some level designation"<br/>           
 * &nbsp;&nbsp;}<br/>      
 *}<br/>
 * ...<br/>
 * 
  @see Configuration
 *@see Level
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
	 * @see com.github.arachnidium.util.configuration.
	 * AbstractConfigurationAccessHelper#getSetting(java.lang.String)
	 */
	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(loggingGroup, name);
	}

}
