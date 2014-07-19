package org.arachnidium.core.webdriversettings;

import org.arachnidium.core.webdriversettings.interfaces.IHasAlertTimeOut;
import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

public class WindowsTimeOuts extends AbstractConfigurationAccessHelper
		implements IHasAlertTimeOut {
	private final String newWindowTimeOutSetting = "newWindowTimeOutSec";
	private final String windowCountTimeOutSetting = "windowCountTimeOutSec";
	private final String windowClosingTimeOutSetting = "windowClosingTimeOutSec";
	private final String windowsTimeOutsGroup = "windowsTimeOuts";

	public WindowsTimeOuts(Configuration configuration) {
		super(configuration);
	}

	public Long getNewWindowTimeOutSec() {
		return (Long) getSetting(newWindowTimeOutSetting);
	}

	@Override
	public Long getSecsForAwaitinAlertPresent() {
		return (Long) getSetting(awaitinForAlertPresentSetting);
	}

	@Override
	public Object getSetting(String name) {
		return getSettingValue(windowsTimeOutsGroup, name);
	}

	public Long getWindowClosingTimeOutSec() {
		return (Long) getSetting(windowClosingTimeOutSetting);
	}

	public Long getWindowCountTimeOutSec() {
		return (Long) getSetting(windowCountTimeOutSetting);
	}

}