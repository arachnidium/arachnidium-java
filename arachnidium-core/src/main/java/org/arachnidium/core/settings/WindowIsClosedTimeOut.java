package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

/**
 * Stores time out of closing the window implicitly waiting<br/>
 * 
 * Setting specification:<br/>
 * 
 * <p><br/>
 * "windowIsClosedTimeOut":<br/>
 * {<br/>
 * &nbsp;&nbsp;"windowIsClosedTimeOut":{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"LONG",<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some long value"<br/>     
 * &nbsp;&nbsp;}<br/>
 * }<br/>
 * </p>
 * 
 * Is applied on browser. Is ignored by mobile apps.
 * 
 * @see Configuration
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
