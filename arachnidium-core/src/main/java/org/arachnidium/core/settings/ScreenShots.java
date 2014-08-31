package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

public class ScreenShots extends AbstractConfigurationAccessHelper {

	private final String toDoScreenShotsOfNewHandles = "toDoScreenShotsOfNewHandles";
	private final String toDoScreenShotsOnElementHighLighting = "toDoScreenShotsOnElementHighLighting";
	// screenshot group
	private final String screenShotssGroup = "screenShots";
	private final Boolean DEFAULT_VALUE = false;

	public ScreenShots(Configuration configuration) {
		super(configuration);
	}

	@Override
	public Object getSetting(String name) {
		return getSettingValue(screenShotssGroup, name);
	}

	private Boolean returnExplicitOrDefaultValue(Boolean value) {
		if (value == null) {
			value = DEFAULT_VALUE;
		}
		return value;
	}

	public Boolean getToDoScreenShotsOfNewHandles() {
		return returnExplicitOrDefaultValue((Boolean) getSetting(toDoScreenShotsOfNewHandles));
	}

	public Boolean getToDoScreenShotsOnElementHighLighting() {
		return returnExplicitOrDefaultValue((Boolean) getSetting(toDoScreenShotsOnElementHighLighting));
	}

}
