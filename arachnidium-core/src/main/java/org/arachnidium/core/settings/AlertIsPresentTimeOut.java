package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

/**
 * Stores time out of alert implicitly waiting
 * 
 * @see Configuration
 * 
 * Setting specification:
 * 
 * <code>...
 * "alertIsPresentTimeOut":
 * {
 *      "alertIsPresentTimeOut":{
 *        "type":"LONG",
 *        "value":"some long value"     
 *       }
 * }
 * ...
 * </code>
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
