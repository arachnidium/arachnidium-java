package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.Alert;

/**
 * Stores time out of alert implicitly waiting
 * 
 * Setting specification:
 * 
 * <p><br/>
 * "alertIsPresentTimeOut":<br/>
 * {<br/>
 * &nbsp;&nbsp;"alertIsPresentTimeOut":{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"LONG",<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some long value"<br/>     
 * &nbsp;&nbsp;}<br/>
 * }<br/>
 * </p>
 * 
 * @see Configuration
 * @see Alert
 */
public class AlertIsPresentTimeOut extends AbstractConfigurationAccessHelper {
	private final String alertIsPresentTimeOutGroup = "alertIsPresentTimeOut";
	private final String alertIsPresentTimeOutSetting = "alertIsPresentTimeOut";

	public AlertIsPresentTimeOut(Configuration configuration) {
		super(configuration);
	}

	/**
	 * @see org.arachnidium.util.configuration.AbstractConfigurationAccessHelper#getSetting(java.lang.String)
	 */
	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(alertIsPresentTimeOutGroup, name);
	}

	/**
	 * @return {@link Long} value of alert implicitly waiting time out
	 */
	public Long getAlertIsPresentTimeOut() {
		return getSetting(alertIsPresentTimeOutSetting);
	}

}
