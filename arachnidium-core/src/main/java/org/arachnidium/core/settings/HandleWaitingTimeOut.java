package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

public class HandleWaitingTimeOut extends AbstractConfigurationAccessHelper {
	private final String handleWaitingTimeOutGroup = "handleWaitingTimeOut";	
	private final String handleWaitingTimeOutSetting = "handleWaitingTimeOut";
	
	public HandleWaitingTimeOut(Configuration configuration) {
		super(configuration);
	}

	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(handleWaitingTimeOutGroup, name);
	}
	
	public Long getHandleWaitingTimeOut(){
		return getSetting(handleWaitingTimeOutSetting);
	}

}
