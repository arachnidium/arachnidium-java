package com.github.arachnidium.core.settings;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

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
@Group(settingGroup = "handleWaitingTimeOut")
public class HandleWaitingTimeOut extends AbstractConfigurationAccessHelper {
	
	protected HandleWaitingTimeOut(Configuration configuration, String group) {
		super(configuration, group);
	}
	
	/**
	 * @return {@link Long} value of {@link Window} or 
	 * context ({@link ContextAware}) implicitly waiting time out
	 */
	@Setting(setting = "handleWaitingTimeOut")
	public Long getHandleWaitingTimeOut(){
		return getSetting();
	}

}
