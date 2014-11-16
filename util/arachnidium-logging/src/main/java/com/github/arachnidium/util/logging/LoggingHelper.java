package com.github.arachnidium.util.logging;

import java.util.logging.Level;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

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
@Group(settingGroup = "Log")
class LoggingHelper extends AbstractConfigurationAccessHelper {

	protected LoggingHelper(Configuration configuration, String group) {
		super(configuration, group);
	}

	/**
	 * @return Specified log {@link Level}
	 */
	@Setting(setting = "Level")
	public Level getLevel() {
		String levelName = getSetting();
		if (levelName != null)
			return Level.parse(levelName.toUpperCase());
		else
			return null;
	}
}
