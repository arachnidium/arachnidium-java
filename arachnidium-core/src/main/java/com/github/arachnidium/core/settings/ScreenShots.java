package com.github.arachnidium.core.settings;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver.Window;

/**
 * Stores these flags:<br/>
 * - to take screenshots when element is highlighted<br/>
 * - to take screenshots when new browser {@link Window} or context (see {@link ContextAware})<br/>
 * is found<br/> 
 * <br/> 
 * Specification:<br/> 
 * 
 * <p><br/>
 * ...<br/>
 *"screenShots":<br/>
 *{<br/>
 *&nbsp;&nbsp;"toTakeScreenShotsOnElementHighLighting":{<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"type":"BOOL",<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"flag value"<br/>           
 *&nbsp;&nbsp;},<br/>
 *&nbsp;&nbsp;"toTakeScreenShotsOfNewHandles":{<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"type":"BOOL",<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"flag value"<br/>           
 *&nbsp;&nbsp;}<br/>              
 *}<br/>
 *...<br/>
 *</p>
 *
 *@see Configuration
 */
@Group(settingGroup = "screenShots")
public class ScreenShots extends AbstractConfigurationAccessHelper {
	// screenshot group
	private final Boolean DEFAULT_VALUE = false;

	protected ScreenShots(Configuration configuration, String group) {
		super(configuration, group);
	}

	private Boolean returnExplicitOrDefaultValue(Boolean value) {
		if (value == null) {
			value = DEFAULT_VALUE;
		}
		return value;
	}

	/**
	 * @return {@link Boolean} value of the flag "toTakeScreenShotsOfNewHandles"
	 */
	@Setting(setting = "toTakeScreenShotsOfNewHandles")
	public Boolean getToTakeScreenShotsOfNewHandles() {
		return returnExplicitOrDefaultValue(getSetting());
	}

	/**
	 * @return {@link Boolean} value of the flag "toTakeScreenShotsOnElementHighLighting"
	 */
	@Setting(setting = "toTakeScreenShotsOnElementHighLighting")
	public Boolean getToTakeScreenShotsOnElementHighLighting() {
		return returnExplicitOrDefaultValue(getSetting());
	}

}
