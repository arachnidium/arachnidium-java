package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver.Window;

/**
 * Stores these flags:
 * - to take screenshots when element is highlighted
 * - to take screenshots when new browser {@link Window} or context (see {@link ContextAware})
 * is found 
 * 
 * @see Configuration
 * 
 * Specification:
 * 
 * ...
 *"screenShots":
  {
      "toTakeScreenShotsOnElementHighLighting":{
          "type":"BOOL",
          "value":"flag value"           
      },
      "toTakeScreenShotsOfNewHandles":{
          "type":"BOOL",
          "value":"flag value"           
      }              
  }
 *...
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
	 * @see org.arachnidium.util.configuration.AbstractConfigurationAccessHelper#getSetting(java.lang.String)
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
