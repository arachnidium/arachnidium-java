package org.arachnidium.core.settings;


import java.util.concurrent.TimeUnit;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.WebDriver.Timeouts;

/** 
 * Specification:
 * 
 * <p><br/>
 * ...<br/>
 * "webDriverTimeOuts":<br/>
 * {<br/>
 * &nbsp;&nbsp;"timeUnit":{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some time unit designation"<br/>           
 * &nbsp;&nbsp;},<br/> 
 * &nbsp;&nbsp;"implicitlyWait":{<br/> 
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"LONG",<br/> 
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some long value"<br/>      
 * &nbsp;&nbsp;},<br/> 
 * &nbsp;&nbsp;"pageLoadTimeout":{<br/> 
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"LONG",<br/> 
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some long value"<br/>      
 * &nbsp;&nbsp;},<br/>  
 * &nbsp;&nbsp;"setScriptTimeout":{<br/> 
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"LONG",<br/> 
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some long value"<br/>      
 * &nbsp;&nbsp;}<br/>        
 *}<br/> 
 * ..<br/> 
 * </p>
 * 
 * @author Stores values of specified time outs
 * @see Configuration
 * @see Timeouts
 * @see TimeUnit
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
