package org.arachnidium.core.webdriversettings;

import org.arachnidium.core.webdriversettings.interfaces.IHasAlertTimeOut;
import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

@Deprecated
public class ContextTimeOuts extends AbstractConfigurationAccessHelper
		implements IHasAlertTimeOut {

	// screenshot group
	private final String contextGroup = "Contexts";

	private final String contextCountTimeOutSetting = "contextCountTimeOutSec";
	private final String newContextTimeOutSetting = "newContextTimeOutSec";
	private final String isContextPresentTimeOutSetting = "isContextPresentTimeOut";

	public ContextTimeOuts(Configuration configuration) {
		super(configuration);
	}

	public Long getContextCountTimeOutSec() {
		return (Long) getSetting(contextCountTimeOutSetting);
	}

	public Long getIsContextPresentTimeOut() {
		return (Long) getSetting(isContextPresentTimeOutSetting);
	}

	public Long getNewContextTimeOutSec() {
		return (Long) getSetting(newContextTimeOutSetting);
	}

	@Override
	public Long getSecsForAwaitinAlertPresent() {
		return (Long) getSetting(awaitinForAlertPresentSetting);
	}

	@Override
	public Object getSetting(String name) {
		return getSettingValue(contextGroup, name);
	}
}
