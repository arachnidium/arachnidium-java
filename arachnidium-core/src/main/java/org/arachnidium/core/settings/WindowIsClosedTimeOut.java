package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

public class WindowIsClosedTimeOut extends AbstractConfigurationAccessHelper {
	private final String windowIsClosedTimeOutGroup = "windowIsClosedTimeOut";	
	private final String windowIsClosedTimeOutSetting = "windowIsClosedTimeOut";
	
	public WindowIsClosedTimeOut(Configuration configuration) {
		super(configuration);
	}

	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(windowIsClosedTimeOutGroup, name);
	}
	
	public Long getWindowIsClosedTimeOutTimeOut(){
		return getSetting(windowIsClosedTimeOutSetting);
	}

}
