package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

/**
 * Stores time out of closing the window implicitly waiting
 * 
 * @see Configuration
 * 
 * Setting specification:
 * 
 * 
  "windowIsClosedTimeOut":
  {
      "windowIsClosedTimeOut":{
          "type":"LONG",
          "value":"some long value"     
      }
  }
 * 
 * Is applied with browser. Is ignored by apps.
 */
public class WindowIsClosedTimeOut extends AbstractConfigurationAccessHelper {
	private final String windowIsClosedTimeOutGroup = "windowIsClosedTimeOut";	
	private final String windowIsClosedTimeOutSetting = "windowIsClosedTimeOut";
	
	public WindowIsClosedTimeOut(Configuration configuration) {
		super(configuration);
	}

	/**
	 * @see org.arachnidium.util.configuration.AbstractConfigurationAccessHelper#getSetting(java.lang.String)
	 */
	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(windowIsClosedTimeOutGroup, name);
	}
	
	
	/**
	 * @return {@link Long} value of closing the window implicitly waiting time out
	 */	
	public Long getWindowIsClosedTimeOutTimeOut(){
		return getSetting(windowIsClosedTimeOutSetting);
	}

}
