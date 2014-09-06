package org.arachnidium.core.settings;

import java.util.concurrent.TimeUnit;

import org.arachnidium.core.settings.interfaces.ITimeUnitSetting;
import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

/**
 * @author s.tihomirov Specified webdriver time out parameters
 */
public class WebDriverTimeOuts extends AbstractConfigurationAccessHelper
implements ITimeUnitSetting {

	private final String implicitlyWaitTimeOutSetting = "implicitlyWait";
	private final String pageLoadTimeoutSetting = "pageLoadTimeout";
	private final String scriptTimeOutSetting = "setScriptTimeout";
	private final String webDriverTimeOutsGroup = "webDriverTimeOuts";
	public final String timeUnitSetting = "timeUnit";

	public WebDriverTimeOuts(Configuration configuration) {
		super(configuration);
	}

	public Long getImplicitlyWaitTimeOut() {
		return getSetting(implicitlyWaitTimeOutSetting);
	}

	public Long getLoadTimeout() {
		return getSetting(pageLoadTimeoutSetting);
	}

	public Long getScriptTimeOut() {
		return getSetting(scriptTimeOutSetting);
	}

	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(webDriverTimeOutsGroup, name);
	}

	@Override
	public TimeUnit getTimeUnit() {
		String timeUnitStr = getSetting(timeUnitSetting);
		if (timeUnitStr != null)
			return TimeUnit.valueOf(timeUnitStr.toUpperCase());
		else
			return null;
	}

}
