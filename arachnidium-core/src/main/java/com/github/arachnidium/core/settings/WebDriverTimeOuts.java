package com.github.arachnidium.core.settings;


import java.util.concurrent.TimeUnit;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

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
@Group(settingGroup = "webDriverTimeOuts")
public class WebDriverTimeOuts extends AbstractConfigurationAccessHelper {

	protected WebDriverTimeOuts(Configuration configuration, String group) {
		super(configuration, group);
	}

	/**
	 * @return {@link Long} value of implicitly wait timeOut
	 */
	@Setting(setting = "implicitlyWait")
	public Long getImplicitlyWaitTimeOut() {
		return getSetting();
	}

	/**
	 * @return {@link Long} value of page load wait timeOut
	 */
	@Setting(setting = "pageLoadTimeout")
	public Long getLoadTimeout() {
		return getSetting();
	}

	/**
	 * @return {@link Long} value of javaScript execution timeOut
	 */
	@Setting(setting = "setScriptTimeout")
	public Long getScriptTimeOut() {
		return getSetting();
	}

	/**
	 * @return Specified {@link TimeUnit}
	 */
	@Setting(setting = "timeUnit")
	public TimeUnit getTimeUnit() {
		String timeUnitStr = getSetting();
		if (timeUnitStr != null)
			return TimeUnit.valueOf(timeUnitStr.toUpperCase());
		else
			return null;
	}

}
