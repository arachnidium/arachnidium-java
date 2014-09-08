package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver.Window;

/**
 * Stores time out of {@link Window} or context ({@link ContextAware}) 
 * implicitly waiting.
 * 
 * @see Configuration
 * 
 * Specification:
 * 
 * ...
 * "handleWaitingTimeOut":
  {
      "handleWaitingTimeOut":{
          "type":"LONG",
          "value":"some long value"     
      }
  }
  ...
 */
public class HandleWaitingTimeOut extends AbstractConfigurationAccessHelper {
	private final String handleWaitingTimeOutGroup = "handleWaitingTimeOut";	
	private final String handleWaitingTimeOutSetting = "handleWaitingTimeOut";
	
	public HandleWaitingTimeOut(Configuration configuration) {
		super(configuration);
	}

	/**
	 * @see org.arachnidium.util.configuration.AbstractConfigurationAccessHelper#getSetting(java.lang.String)
	 */
	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(handleWaitingTimeOutGroup, name);
	}
	
	/**
	 * @return {@link Long} value of {@link Window} or 
	 * context ({@link ContextAware}) implicitly waiting time out
	 */
	public Long getHandleWaitingTimeOut(){
		return getSetting(handleWaitingTimeOutSetting);
	}

}
