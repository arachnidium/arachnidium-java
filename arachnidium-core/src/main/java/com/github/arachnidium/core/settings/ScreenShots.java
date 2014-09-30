package com.github.arachnidium.core.settings;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
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
public class ScreenShots extends AbstractConfigurationAccessHelper {

	private final String toTakeScreenShotsOfNewHandles = "toTakeScreenShotsOfNewHandles";
	private final String toTakeScreenShotsOnElementHighLighting = "toTakeScreenShotsOnElementHighLighting";
	// screenshot group
	private final String screenShotssGroup = "screenShots";
	private final Boolean DEFAULT_VALUE = false;

	public ScreenShots(Configuration configuration) {
		super(configuration);
	}

	/**
	 * @see com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper#getSetting(java.lang.String)
	 */
	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(screenShotssGroup, name);
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
	public Boolean getToTakeScreenShotsOfNewHandles() {
		return returnExplicitOrDefaultValue(getSetting(toTakeScreenShotsOfNewHandles));
	}

	/**
	 * @return {@link Boolean} value of the flag "toTakeScreenShotsOnElementHighLighting"
	 */
	public Boolean getToTakeScreenShotsOnElementHighLighting() {
		return returnExplicitOrDefaultValue(getSetting(toTakeScreenShotsOnElementHighLighting));
	}

}
