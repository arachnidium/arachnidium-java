package com.github.arachnidium.core.settings;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

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
@Group(settingGroup = "windowIsClosedTimeOut")
public class WindowIsClosedTimeOut extends AbstractConfigurationAccessHelper {
	protected WindowIsClosedTimeOut(Configuration configuration, String group) {
		super(configuration, group);
	}	
	
	/**
	 * @return {@link Long} value of closing the window implicitly waiting time out
	 */	
	@Setting(setting = "windowIsClosedTimeOut")
	public Long getWindowIsClosedTimeOutTimeOut(){
		return getSetting();
	}

}
