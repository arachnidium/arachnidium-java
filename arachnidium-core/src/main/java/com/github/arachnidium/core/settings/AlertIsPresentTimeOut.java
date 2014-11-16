package com.github.arachnidium.core.settings;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

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
@Group(settingGroup = "alertIsPresentTimeOut")
public class AlertIsPresentTimeOut extends AbstractConfigurationAccessHelper {

	protected AlertIsPresentTimeOut(Configuration configuration, String group) {
		super(configuration, group);
	}
	
	/**
	 * @return {@link Long} value of alert implicitly waiting time out
	 */
	@Setting(setting = "alertIsPresentTimeOut")
	public Long getAlertIsPresentTimeOut() {
		return getSetting();
	}

}
