package org.arachnidium.core.settings;


import java.util.concurrent.TimeUnit;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.WebDriver.Timeouts;

/**
 * @author Stores values of specified time outs
 * @see Configuration
 * @see Timeouts
 * 
 * Specification:
 * 
 * ...
 * "webDriverTimeOuts":
  {
      "timeUnit":{
          "type":"STRING",
          "value":"some time unit designation" @see {@link TimeUnit}           
      },
      "implicitlyWait":{
          "type":"LONG",
          "value":"some long value"     
      },
      "pageLoadTimeout":{
          "type":"LONG",
          "value":"some long value"     
      }, 
      "setScriptTimeout":{
          "type":"LONG",
          "value":"some long value"     
      }       
  }
 * ..
 */
public class WebDriverTimeOuts extends AbstractConfigurationAccessHelper {

	private final String implicitlyWaitTimeOutSetting = "implicitlyWait";
	private final String pageLoadTimeoutSetting = "pageLoadTimeout";
	private final String scriptTimeOutSetting = "setScriptTimeout";
	private final String webDriverTimeOutsGroup = "webDriverTimeOuts";
	public final String timeUnitSetting = "timeUnit";

	public WebDriverTimeOuts(Configuration configuration) {
		super(configuration);
	}

	/**
	 * @return {@link Long} value of implicitly wait timeOut
	 */
	public Long getImplicitlyWaitTimeOut() {
		return getSetting(implicitlyWaitTimeOutSetting);
	}

	/**
	 * @return {@link Long} value of page load wait timeOut
	 */
	public Long getLoadTimeout() {
		return getSetting(pageLoadTimeoutSetting);
	}

	/**
	 * @return {@link Long} value of javaScript execution timeOut
	 */
	public Long getScriptTimeOut() {
		return getSetting(scriptTimeOutSetting);
	}

	/**
	 * @see org.arachnidium.util.configuration.AbstractConfigurationAccessHelper#getSetting(java.lang.String)
	 */
	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(webDriverTimeOutsGroup, name);
	}

	/**
	 * @return Specified {@link TimeUnit}
	 */
	public TimeUnit getTimeUnit() {
		String timeUnitStr = getSetting(timeUnitSetting);
		if (timeUnitStr != null)
			return TimeUnit.valueOf(timeUnitStr.toUpperCase());
		else
			return null;
	}

}
