package org.arachnidium.util.configuration.commonhelpers;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.configuration.interfaces.IHasAlertTimeOut;


public class ContextTimeOuts extends AbstractConfigurationAccessHelper implements IHasAlertTimeOut{

	public ContextTimeOuts(Configuration configuration) {
		super(configuration);
	}

	// screenshot group
	private final String contextGroup = "Contexts";
	private final String contextCountTimeOutSetting = "contextCountTimeOutSec";
	private final String newContextTimeOutSetting   = "newContextTimeOutSec";
	private final String isContextPresentTimeOutSetting  = "isContextPresentTimeOut";

	
	@Override
	public Object getSetting(String name) {
		return getSettingValue(contextGroup, name);
	}
	
	public Long getContextCountTimeOutSec(){
		return (Long) getSetting(contextCountTimeOutSetting); 
	}

	public Long getNewContextTimeOutSec() {
		return (Long) getSetting(newContextTimeOutSetting);
	}
	
	public Long getIsContextPresentTimeOut() {
		return (Long) getSetting(isContextPresentTimeOutSetting);
	}
	
	@Override
	public Long getSecsForAwaitinAlertPresent() {
		return (Long) getSetting(awaitinForAlertPresentSetting);
	}
}
