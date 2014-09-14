package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver.Window;

/**
 * Stores time out of {@link Window} or context ({@link ContextAware}) 
 * implicitly waiting.
 * 
 * Specification:
 * 
 * <p><br/>
 *...<br/>
 *"handleWaitingTimeOut":<br/>
 *{<br/>
 *&nbsp;&nbsp;"handleWaitingTimeOut":{<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"type":"LONG",<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"some long value"<br/>     
 *&nbsp;&nbsp;}<br/>
 *}<br/>
 *...<br/>
 *</p>
 *
 *@see Configuration
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
