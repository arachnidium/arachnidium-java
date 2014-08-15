package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;

public class ScreenShots extends AbstractConfigurationAccessHelper {

	private final String toDoScreenShotsOfNewHandles = "toDoScreenShotsOfNewHandles";
	private final String toDoScreenShotsOnElementHighLighting = "toDoScreenShotsOnElementHighLighting";
	// screenshot group
	private final String screenShotssGroup = "screenShots";

	public ScreenShots(Configuration configuration) {
		super(configuration);
	}

	@Override
	public Object getSetting(String name) {
		return getSettingValue(screenShotssGroup, name);
	}

	public Boolean getToDoScreenShotsOfNewHandles() {
		return (Boolean) getSetting(toDoScreenShotsOfNewHandles);
	}

	public Boolean getToDoScreenShotsOnElementHighLighting() {
		return (Boolean) getSetting(toDoScreenShotsOnElementHighLighting);
	}

}
